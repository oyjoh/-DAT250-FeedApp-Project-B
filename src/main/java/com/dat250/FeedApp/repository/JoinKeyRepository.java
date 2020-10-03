package com.dat250.FeedApp.repository;

import com.dat250.FeedApp.model.JoinKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinKeyRepository extends JpaRepository<JoinKey, Long> {

    Optional<JoinKey> findByKey(Long joinKeyKey);
}