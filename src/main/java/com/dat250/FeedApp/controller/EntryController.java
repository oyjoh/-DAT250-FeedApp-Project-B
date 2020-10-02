package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.EntryRepository;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;

@RestController
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @GetMapping("/poll/{pollId}/entries")
    public List<Entry> getEntries(@PathVariable Long pollId) {
        return pollRepository.findById(pollId).map(poll -> entryRepository.findByPoll(poll))
                .orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    @PostMapping("/poll/{pollId}/entry")
    public Entry createNewEntry(@RequestParam Long personId, @PathVariable Long pollId, @Validated @RequestBody Entry entry) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
        System.out.println(person);
        System.out.println(poll);
        System.out.println(Entry.from(entry, person, poll));
        return entryRepository.save(Entry.from(entry, person, poll));
    }


    @PutMapping("/entry/{entryId}")
    public Entry updatePoll(@PathVariable Long entryId, @Validated @RequestBody Entry entryRequest) {
        return entryRepository.findById(entryId).map(entry -> {
            //entry.setNumber(entryRequest.getNumber());
            entry.setValue(entryRequest.getValue());
            return entryRepository.save(entry);
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long entryId) {
        return entryRepository.findById(entryId).map(entry -> {
            entryRepository.delete(entry);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }
}

