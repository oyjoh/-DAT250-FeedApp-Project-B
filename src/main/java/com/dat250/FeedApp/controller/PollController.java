package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("/poll")
    public List<Poll> getAllPersons(){
        return pollRepository.findAll();
    }

    @PostMapping("/poll")
    public Poll createPoll(@Validated @RequestBody Poll poll){
        return pollRepository.save(poll);
    }

    @PutMapping("/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long pollId, @Validated @RequestBody Poll pollRequest){
        return pollRepository.findById(pollId).map(poll -> {
            poll.setSummary(pollRequest.getSummary());
            poll.setIsPublic(pollRequest.getIsPublic());
            poll.setEntries(pollRequest.getEntries());
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    @DeleteMapping("/poll/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        return pollRepository.findById(pollId).map(poll -> {
            pollRepository.delete(poll);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }
}
