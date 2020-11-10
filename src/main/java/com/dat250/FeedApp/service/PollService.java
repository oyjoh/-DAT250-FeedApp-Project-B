package com.dat250.FeedApp.service;

import com.dat250.FeedApp.event.pollEvents.PollCreatedEvent;
import com.dat250.FeedApp.event.pollEvents.PollUpdatedEvent;
import com.dat250.FeedApp.model.JoinKey;
import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.JoinKeyRepository;
import com.dat250.FeedApp.repository.PersonRepository;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PollService {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final PollRepository pollRepository;
    private final PersonRepository personRepository;
    private final JoinKeyRepository joinKeyRepository;

    @Autowired
    public PollService(ApplicationEventPublisher applicationEventPublisher, PollRepository pollRepository, PersonRepository personRepository, JoinKeyRepository joinKeyRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.pollRepository = pollRepository;
        this.personRepository = personRepository;
        this.joinKeyRepository = joinKeyRepository;
    }

    public List<Poll> getAllPollsThatArePublic(String show) {
        if (show.equals("all")) return pollRepository.findAll(); //?show=all Shows all polls
        return pollRepository.getAllByIsPublic(show.equals("public")); //?show=public //?show=hidden shows public/non-public polls
    }

    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " notFound"));
    }

    public Poll getPollByJoinKey(Long joinKey) {
        return joinKeyRepository.findByKey(joinKey).map(
                joinKey1 -> pollRepository.findByJoinKey(joinKey1).orElseThrow(() -> new ResourceNotFoundException("PollJoinKey: " + joinKey + " notFound"))
        ).orElseThrow(() -> new ResourceNotFoundException("JoinKey: " + joinKey + " notFound"));
    }

    public Poll updatePoll(Long personId, Long pollId) {
        return pollRepository.findByPollIdAndPersonPersonId(pollId, personId).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    public List<Poll> getAllPollsFromPerson(@PathVariable(value = "personId") Long personId) {
        return personRepository.findById(personId).map(pollRepository::findByPerson)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    public Poll createPoll(Long personId, Poll poll) {
        return personRepository.findById(personId).map(person -> {
            JoinKey joinKey = createNewJoinKey();
            joinKeyRepository.save(joinKey);
            poll.setJoinKey(joinKey);
            poll.setPerson(person);
            PollCreatedEvent pollCreatedEvent = new PollCreatedEvent(poll);
            applicationEventPublisher.publishEvent(pollCreatedEvent);
            return pollRepository.save(poll);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
    }

    private JoinKey createNewJoinKey() {
        Random random = new Random();
        Optional<JoinKey> optionalJoinKey;
        long randomNum;
        int min = 0, max = 1000000;
        do {
            randomNum = (long) random.nextInt((max - min) + 1) + min;
            optionalJoinKey = joinKeyRepository.findByKey(randomNum);
        } while (optionalJoinKey.isPresent());

        JoinKey joinKey = new JoinKey();
        joinKey.setKey(randomNum);
        return joinKey;
    }

    public Poll updatePoll(Long personId, Long pollId, Poll pollRequest) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " notFound"));
        //TODO there should be a test to see if the person making the change is the owner OR and ADMIN
        if (poll.getPerson() != person)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person: " + person.getName() + " is not the poll Owner");
        if (pollRequest.getSummary() != null) poll.setSummary(pollRequest.getSummary());
        if (pollRequest.getIsPublic() != null) poll.setIsPublic(pollRequest.getIsPublic());
        PollUpdatedEvent pollUpdatedEvent = new PollUpdatedEvent(poll);
        applicationEventPublisher.publishEvent(pollUpdatedEvent);
        return pollRepository.save(poll);
    }

    public ResponseEntity<?> deletePoll(Long pollId) {
        return pollRepository.findById(pollId).map(poll -> {
            pollRepository.delete(poll);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PollId: " + pollId + " not found"));
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public List<Poll> getAllByIsPublic(boolean aPublic) {
        return pollRepository.getAllByIsPublic(aPublic);
    }
}
