package com.dat250.FeedApp.security;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Role;
import com.dat250.FeedApp.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    private final PersonService personService;

    @PostMapping("/register")
    String register(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password,
            @RequestParam("name") final String name) {

        Person person = new Person();
        person.setEmail(email);
        person.setHash(password);
        person.setName(name);

        personService.createPerson(person);

        return login(email, password);
    }

    @PostMapping("/login")
    String login(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password) {
        return authentication
                .login(email, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}

