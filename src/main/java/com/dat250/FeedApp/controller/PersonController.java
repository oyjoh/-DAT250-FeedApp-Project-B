package com.dat250.FeedApp.controller;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Validated @RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/people/{personId}")
    public Person updatePerson(@PathVariable Long personId, @Validated @RequestBody Person personRequest) {
        return personService.updatePerson(personId, personRequest);
    }

    @DeleteMapping("/people/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId) {
        return personService.deletePerson(personId);
    }
}
