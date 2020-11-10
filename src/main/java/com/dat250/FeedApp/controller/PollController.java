package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/polls")
    public List<Poll> getAllPollsThatArePublic(@RequestParam(defaultValue = "public", required = false) String show) {
        if (show.equals("all")) return pollService.findAll(); //?show=all Shows all polls
        return pollService.getAllByIsPublic(show.equals("public")); //?show=public //?show=hidden shows public/non-public polls
    }

    @GetMapping("/polls/{pollId}")
    public Poll getPollById(@PathVariable Long pollId) {
        return pollService.getPollById(pollId);
    }

    @GetMapping("/polls/joinkey/{joinKey}")
    public Poll getPollByJoinKey(@PathVariable Long joinKey) {
        return pollService.getPollByJoinKey(joinKey);
    }

    @GetMapping("/people/{personId}/poll/{pollId}")
    public Poll updatePoll(@PathVariable Long personId, @PathVariable Long pollId) {
        return pollService.updatePoll(personId, pollId);
    }

    @GetMapping("/people/{personId}/polls")
    public List<Poll> getAllPollsFromPerson(@PathVariable(value = "personId") Long personId) {
        return pollService.getAllPollsFromPerson(personId);
    }

    //TODO should be personID, pollID
    @PostMapping("/people/{personId}/polls")
    @ResponseStatus(HttpStatus.CREATED)
    public Poll getAPollFromAPerson(@PathVariable(value = "personId") Long personId, @Validated @RequestBody Poll poll) {
        return pollService.getAPollFromAPerson(personId, poll);
    }

    @PutMapping("/people/{personId}/polls/{pollId}")
    public Poll updatePoll(@PathVariable Long personId, @PathVariable Long pollId, @Validated @RequestBody Poll pollRequest) {
        return pollService.updatePoll(personId, pollId, pollRequest);
    }

    // TODO REVISIT
    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId, @AuthenticationPrincipal final Person user) {
        Poll p = pollService.getPollById(pollId);
        if (p.getPerson().getPersonId().equals(user.getPersonId())) {
            return pollService.deletePoll(pollId);
        }
        System.out.println("ACCESS DENIED");
        return null;
    }
}
