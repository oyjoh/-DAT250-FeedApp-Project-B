package com.dat250.FeedApp.repository;

import com.dat250.FeedApp.model.Entry;
import com.dat250.FeedApp.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Page<Entry> findByPoll(Poll poll, Pageable pageable);
    Optional<Entry> findByEntryIdAndPoll(Long entryId, Poll poll);
}