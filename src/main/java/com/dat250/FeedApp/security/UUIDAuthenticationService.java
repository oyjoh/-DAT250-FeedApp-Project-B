package com.dat250.FeedApp.security;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
    @NonNull
    private final PersonService personService;

    @Override
    public Optional<String> login(final String email, final String password) {

        Person p = personService.getPersonByEmail(email);

        if (!p.getPassword().equals(password)) {
            return Optional.ofNullable(null);
        }

        System.out.println("person pass: " + p.getPassword());
        System.out.println("insert pass: " + password);

        final String uuid = p.getName() + p.getName(); // proof of concept
        System.out.println("Login success for user: " + p.getEmail());
        return Optional.of(uuid);
    }

    @Override
    public Optional<Person> findByToken(final String token) {
        return Optional.ofNullable(personService.getPerson(Long.valueOf(token)));
    }

    @Override
    public void logout(final Person user) {

    }
}
