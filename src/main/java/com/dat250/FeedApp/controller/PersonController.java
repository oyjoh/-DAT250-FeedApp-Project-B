package com.dat250.FeedApp.controller;


import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.lambdaworks.crypto.SCryptUtil;


import java.util.List;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @GetMapping("/people")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/people/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Validated @RequestBody Person person) {
        try{
            person.setHash(encryptPassword(person.getName()));
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e){
            System.out.println("Person: " + person + " already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person " + person.getName() + " already exists", e);
        }
    }

    @PutMapping("/people/{personId}")
    public Person updatePerson(@PathVariable Long personId, @Validated @RequestBody Person personRequest) {
        return personRepository.findById(personId).map(person -> {
            if(personRequest.getName() != null) person.setName(personRequest.getName());
            if(personRequest.getEmail() != null) person.setEmail(personRequest.getEmail());
            if(personRequest.getHash() != null) person.setHash(encryptPassword(personRequest.getHash()));
            if(personRequest.getRoles() != null) person.setRoles(personRequest.getRoles());
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }

    private String encryptPassword(String password){
        int N = 16384, r = 8, p = 1;
        return SCryptUtil.scrypt(password, N, r, p);
    }

    @DeleteMapping("/people/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId) {
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }
}
