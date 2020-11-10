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
    public Optional<Person> login(final String email, final String password) {
        final String uuid = UUID.randomUUID().toString();
        Person user = personService.getPersonByEmail(email);

        if (!personService.checkPassword(password, user.getHash())) {
            return Optional.ofNullable(null);
        }



        //final String uuid = p.getName(); // proof of concept

        personService.setCookie(email, uuid);

        System.out.println(user.getEmail() + " logged in");
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<Person> findByToken(final String token) {
        return Optional.ofNullable(personService.getPersonByCookie(token));
    }

    @Override
    public void logout(final Person user) {
        System.out.println(user.getEmail() + " logged out");
        personService.setCookie(user.getEmail(), ""); // clear cookie
    }
}
