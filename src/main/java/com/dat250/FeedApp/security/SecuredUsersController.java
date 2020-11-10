package com.dat250.FeedApp.security;

import com.dat250.FeedApp.model.Person;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(value = "/api")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {
    @NonNull
    UserAuthenticationService authentication;

    @GetMapping("/users/current")
    Person getCurrent(@AuthenticationPrincipal final Person user) {
        return user;
    }

    @GetMapping("/users/logout")
    boolean logout(@AuthenticationPrincipal final Person user) {
        authentication.logout(user);
        return true;
    }
}