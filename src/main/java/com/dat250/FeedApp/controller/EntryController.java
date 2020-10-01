package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("/entry")
    public List<Entry> getAllPersons(){
        return entryRepository.findAll();
    }

    @PostMapping("/entry")
    public Entry createPoll(@Validated @RequestBody Entry entry){
        return entryRepository.save(entry);
    }

    @PutMapping("/entry/{entryId}")
    public Entry updatePoll(@PathVariable Long entryId, @Validated @RequestBody Entry entryRequest){
        return entryRepository.findById(entryId).map(entry -> {
            //entry.setNumber(entryRequest.getNumber());
            entry.setValue(entryRequest.getValue());
            return entryRepository.save(entry);
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long entryId){
        return entryRepository.findById(entryId).map(entry -> {
            entryRepository.delete(entry);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EntryId: " + entryId + " not found"));
    }
}

