package com.dat250.FeedApp.service;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.repository.PersonRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }

    public Person getPersonByEmail(String email) {
        return personRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email: " + email + " not found"));
    }

    public Person createPerson(Person person) {
        try{
            //person.setHash(encryptPassword(person.getHash()));
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e){
            System.out.println("Person: " + person + " already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person " + person.getName() + " already exists", e);
        }
    }

    public Person updatePerson(Long personId, Person personRequest) {
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

    public ResponseEntity<?> deletePerson(Long personId) {
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId: " + personId + " not found"));
    }
}

