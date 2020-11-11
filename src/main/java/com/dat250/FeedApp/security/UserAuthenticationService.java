package com.dat250.FeedApp.security;

import com.dat250.FeedApp.model.Person;

import java.util.Optional;

public interface UserAuthenticationService {
    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param email
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<Person> login(String email, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<Person> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param person the user to logout
     */
    void logout(Person person);
}
