package com.dat250.FeedApp.factories;

import com.dat250.FeedApp.model.JoinKey;
import com.dat250.FeedApp.repository.JoinKeyRepository;

import java.util.Optional;
import java.util.Random;

public class JoinKeyFactory {

    public static JoinKey createNewJoinKey(JoinKeyRepository joinKeyRepository) {
        Random random = new Random();
        Optional<JoinKey> optionalJoinKey;
        long randomNum;
        int min = 0, max = 1000000;
        do {
            randomNum = (long) random.nextInt((max - min) + 1) + min;
            optionalJoinKey = joinKeyRepository.findByKey(randomNum);
        } while (optionalJoinKey.isPresent());

        JoinKey joinKey = new JoinKey();
        joinKey.setKey(randomNum);
        joinKeyRepository.save(joinKey);
        return joinKey;
    }
}
