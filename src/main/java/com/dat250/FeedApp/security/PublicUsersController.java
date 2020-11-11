package com.dat250.FeedApp.security;

import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Role;
import com.dat250.FeedApp.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(value = "/api")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    private final PersonService personService;

    @PostMapping("/public/register")
    @ResponseStatus(HttpStatus.CREATED)
    Person register(@Validated @RequestBody Person person) {
        String password = person.getHash();

        Set<Role> set = new HashSet<>();
        set.add(Role.USER);
        person.setRoles(set);  // ensure that users can only register as regular user

        Person p = personService.createPerson(person);

        return login(p.getEmail(), password);
    }

    @PostMapping("/public/login")
    Person login(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password) {
        return authentication
                .login(email, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}

