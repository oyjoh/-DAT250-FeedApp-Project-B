package com.dat250.FeedApp.service;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.EntryRepository;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final PollRepository pollRepository;
    private final PersonRepository personRepository;

    @Autowired
    public EntryService(EntryRepository entryRepository, PollRepository pollRepository, PersonRepository personRepository) {
        this.entryRepository = entryRepository;
        this.pollRepository = pollRepository;
        this.personRepository = personRepository;
    }

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public List<Entry> getEntries(Long pollId) {
        return pollRepository.findById(pollId).map(entryRepository::findByPoll)
                .orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    public String getSimpleEntries(Long pollId) {
        return pollRepository.findById(pollId).map(poll -> {
            List<Entry> simpleEntries = new ArrayList<>();
            List<Entry> entries = entryRepository.findByPoll(poll);
            for(Entry entry : entries){
                simpleEntries.add(Entry.simpleEntry(entry));
            }
            return new Gson().toJson(simpleEntries);
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    public Entry createNewEntry(Long personId, Long pollId, Entry entry) throws Exception {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
        if(poll.getEnded()) throw new Exception("poll has ended");
        for(Entry currEntry : poll.getEntries()){
            if(currEntry.getPerson() == person){
                throw new Exception("person has already voted on poll");
            }
        }
        return entryRepository.save(Entry.from(entry, person, poll));
    }

    public Entry updateEntry(Long pollId, Long entryId, Entry entryRequest) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
        return entryRepository.findByEntryIdAndPoll(entryId, poll).map(entry -> {
            if (entryRequest.getValue() != null) entry.setValue(entryRequest.getValue());
            if (entryRequest.getNumber() != null) entry.setNumber(entryRequest.getNumber());
            return entryRepository.save(entry);
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found in Poll: " + pollId));
    }

    public ResponseEntity<?> deleteEntry(Long entryId) {
        return entryRepository.findById(entryId).map(entry -> {
            entryRepository.delete(entry);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }
}

