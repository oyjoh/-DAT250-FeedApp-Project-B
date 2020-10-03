package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PollController {

    private final PollRepository pollRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PollController(PollRepository pollRepository, PersonRepository personRepository){
        this.pollRepository = pollRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/polls")
    public List<Poll> getAllPollsThatArePublic(@RequestParam(defaultValue = "public", required=false) String show) {
        if(show.equals("all")) return pollRepository.findAll(); //?show=all Shows all polls
        return pollRepository.getAllByIsPublic(show.equals("public")); //?show=public //?show=hidden shows public/non-public polls
    }

    @GetMapping("/polls/{pollId}")
    public Poll getPollById(@PathVariable Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " notFound"));
    }

    @GetMapping("/people/{personId}/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long personId, @PathVariable Long pollId){
        return pollRepository.findByPollIdAndPersonPersonId(pollId, personId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    @GetMapping("/people/{personId}/polls")
    public List<Poll> getAllPollsFromPerson(@PathVariable (value = "personId") Long personId) {
        return personRepository.findById(personId).map(pollRepository::findByPerson)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    @PostMapping("/people/{personId}/polls")
    public Poll getAPollFromAPerson(@PathVariable(value = "personId") Long personId, @Validated @RequestBody Poll poll){
        return personRepository.findById(personId).map(person -> {
            poll.setPerson(person);
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    @PutMapping("/people/{personId}/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long personId, @PathVariable Long pollId, @Validated @RequestBody Poll pollRequest){
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
        //TODO there should be a test to see if the person making the change is the owner OR and ADMIN
        if(poll.getPerson() != person) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person: " + person.getName() + " is not the poll Owner");
        if(pollRequest.getSummary() != null) poll.setSummary(pollRequest.getSummary());
        if(pollRequest.getIsPublic() != null) poll.setIsPublic(pollRequest.getIsPublic());
        return pollRepository.save(poll);
    }

    @DeleteMapping("/poll/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        return pollRepository.findById(pollId).map(poll -> {
            pollRepository.delete(poll);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }
}
