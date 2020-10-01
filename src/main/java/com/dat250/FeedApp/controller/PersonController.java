package com.dat250.FeedApp.controller;


import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/people")
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    @GetMapping("/people/{personId}")
    public Person getPerson(@PathVariable Long personId){
        return personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }

    @PostMapping("/people")
    public Person createPerson(@Validated @RequestBody Person person){
        return personRepository.save(person);
    }

    @PutMapping("/people/{personId}")
    public Person updatePerson(@PathVariable Long personId, @Validated @RequestBody Person personRequest){
        return personRepository.findById(personId).map(person -> {
            person.setName(personRequest.getName());
            person.setEmail(personRequest.getEmail());
            person.setHash(personRequest.getHash());
            person.setPolls(personRequest.getPolls());
            person.setRoles(personRequest.getRoles());
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }

    @DeleteMapping("/people/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId){
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }
}
