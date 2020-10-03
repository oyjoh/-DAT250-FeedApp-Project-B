package com.dat250.FeedApp.repository;


import com.dat250.FeedApp.model.JoinKey;
import com.dat250.FeedApp.model.Person;
import com.dat250.FeedApp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> getAllByIsPublic(Boolean isPublic);

    List<Poll> findByPerson(Person person);

    Optional<Poll> findByJoinKey(JoinKey joinKey);

    Optional<Poll> findByPollIdAndPersonPersonId(Long pollId, Long personId);

}