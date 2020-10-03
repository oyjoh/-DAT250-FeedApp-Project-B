package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.EntryRepository;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EntryController {

    private final EntryRepository entryRepository;
    private final PollRepository pollRepository;
    private final PersonRepository personRepository;

    @Autowired
    public EntryController(EntryRepository entryRepository, PollRepository pollRepository, PersonRepository personRepository){
        this.entryRepository = entryRepository;
        this.pollRepository = pollRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @GetMapping("/polls/{pollId}/entries")
    public List<Entry> getEntries(@PathVariable Long pollId) {
        return pollRepository.findById(pollId).map(entryRepository::findByPoll)
                .orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    @GetMapping("/polls/{pollId}/simpleEntries")
    public MappingJacksonValue getSimpleEntries(@PathVariable Long pollId) {
        return pollRepository.findById(pollId).map(poll -> {
            List<Entry> entries = entryRepository.findByPoll(poll);
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value", "number", "createdAt");
            FilterProvider filters = new SimpleFilterProvider().addFilter("SimpleEntryFilter", filter);
            MappingJacksonValue mapping = new MappingJacksonValue(entries);
            mapping.setFilters(filters);
            return mapping;
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    @PostMapping("/polls/{pollId}/entry")
    public Entry createNewEntry(@RequestParam Long personId, @PathVariable Long pollId, @Validated @RequestBody Entry entry) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
        return entryRepository.save(Entry.from(entry, person, poll));
    }

    @PutMapping("/polls/{pollId}/entry/{entryId}")
    public Entry updateEntry(@PathVariable Long pollId, @PathVariable Long entryId, @Validated @RequestBody Entry entryRequest) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
        return entryRepository.findByEntryIdAndPoll(entryId, poll).map(entry -> {
            if(entryRequest.getValue() != null) entry.setValue(entryRequest.getValue());
            if(entryRequest.getNumber() != null) entry.setNumber(entryRequest.getNumber());
            return entryRepository.save(entry);
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found in Poll: " + pollId));
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long entryId) {
        return entryRepository.findById(entryId).map(entry -> {
            entryRepository.delete(entry);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }
}

