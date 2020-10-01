package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.PersonRepository;
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
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/polls")
    public List<Poll> getAllPolls(){
        return pollRepository.findAll();
    }

    @GetMapping("/people/{personId}/polls")
    public List<Poll> getAllPollsFromPerson(@PathVariable (value = "personId") Long personId){
        return pollRepository.findByPersonUserId(personId);
    }

    @PostMapping("/people/{personId}/polls")
    public Poll createPoll(@PathVariable(value = "personId") Long personId, @Validated @RequestBody Poll poll){
        return personRepository.findById(personId).map(person -> {
            poll.setPerson(person);
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    /*
    @PutMapping("/person/{personId}/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long pollId, @Validated @RequestBody Poll pollRequest){
        return pollRepository.findById(pollId).map(poll -> {
            poll.setSummary(pollRequest.getSummary());
            poll.setIsPublic(pollRequest.getIsPublic());
            poll.setEntries(pollRequest.getEntries());
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }
     */

    @DeleteMapping("/poll/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        return pollRepository.findById(pollId).map(poll -> {
            pollRepository.delete(poll);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }
}
