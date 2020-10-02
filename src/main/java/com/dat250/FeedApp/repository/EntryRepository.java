package com.dat250.FeedApp.repository;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByPoll(Poll poll);
    Optional<Entry> findByEntryIdAndPoll(Long entryId, Poll poll);
}