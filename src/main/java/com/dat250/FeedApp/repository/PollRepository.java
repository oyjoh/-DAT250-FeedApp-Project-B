package com.dat250.FeedApp.repository;


import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    List<Poll> getAllByPerson(Person person);

}