package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/people/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    /*    Replaced by PublicUsersController
        @PostMapping("/people")
        @ResponseStatus(HttpStatus.CREATED)
        public Person createPerson(@Validated @RequestBody Person person) {
            return personService.createPerson(person);
        }
     */

    @PutMapping("/people/{personId}")
    public Person updatePerson(@PathVariable Long personId, @Validated @RequestBody Person personRequest
            , @AuthenticationPrincipal final Person user) {
        if (personId.equals(user.getPersonId()) || user.isAdmin()) {
            return personService.updatePerson(personId, personRequest);
        } else {
            System.err.println("access denied for user: " + user.getName());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person: " + user.getName() + " is an imposter");
        }

    }

    @DeleteMapping("/people/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId, @AuthenticationPrincipal final Person user) {
        if (personId.equals(user.getPersonId()) || user.isAdmin()) {
            return personService.deletePerson(personId);
        } else {
            System.err.println("access denied for user: " + user.getName());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person: " + user.getName() + " is an imposter");
        }
    }
}
