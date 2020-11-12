package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class EntryController {

    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/polls/{pollId}/entries")
    public List<Entry> getEntries(@PathVariable Long pollId) {
        return entryService.getEntries(pollId);
    }

    @GetMapping(value = "/polls/{pollId}/simpleEntries", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSimpleEntries(@PathVariable Long pollId) {
        return entryService.getSimpleEntries(pollId);
    }

    @PostMapping("/polls/{pollId}/entry")
    @ResponseStatus(HttpStatus.CREATED)
    public Serializable createNewEntry(@RequestParam Long personId, @PathVariable Long pollId, @Validated @RequestBody Entry entry) throws Exception {
        return entryService.createNewEntry(personId, pollId, entry);
    }

    @PutMapping("/polls/{pollId}/entry/{entryId}")
    public Entry updateEntry(@PathVariable Long pollId, @PathVariable Long entryId, @Validated @RequestBody Entry entryRequest) {
        return entryService.updateEntry(pollId, entryId, entryRequest);
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long entryId) {
        return entryService.deleteEntry(entryId);
    }
}

