package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/polls") //Can set ?isPublic=false to see all poll, not just public ones
    public List<Poll> getAllPollsThatArePublic(@RequestParam(defaultValue = "true", required=false) String isPublic) {
        return pollRepository.getAllByIsPublic(Boolean.parseBoolean(isPublic));
    }

    @GetMapping("/people/{personId}/polls")
    public List<Poll> getAllPollsFromPerson(@PathVariable (value = "personId") Long personId) {
        return personRepository.findById(personId).map(person ->
                pollRepository.findByPerson(person))
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    @PostMapping("/people/{personId}/polls")
    public Poll createPoll(@PathVariable(value = "personId") Long personId, @Validated @RequestBody Poll poll){
        return personRepository.findById(personId).map(person -> {
            poll.setPerson(person);
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    @PutMapping("/person/{personId}/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long pollId, @Validated @RequestBody Poll pollRequest, @PathVariable String personId){
        return pollRepository.findById(pollId).map(poll -> {
            poll.setSummary(pollRequest.getSummary());
            poll.setIsPublic(pollRequest.getIsPublic());
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
