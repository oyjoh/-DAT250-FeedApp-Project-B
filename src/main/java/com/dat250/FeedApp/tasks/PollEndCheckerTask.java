package com.dat250.FeedApp.tasks;

import com.dat250.FeedApp.event.pollEvents.PollEndedEvent;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.repository.PollRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PollEndCheckerTask {
    private final PollRepository pollRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public PollEndCheckerTask(PollRepository pollRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.pollRepository = pollRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkForEndedPolls() {
        Date date = new Date();
        List<Poll> endedList = pollRepository.getByEndAtLessThanAndEndedFalse(date);
        for(Poll poll : endedList) {
            poll.setEnded(true);
            pollRepository.save(poll);
            PollEndedEvent pollEndedEvent = new PollEndedEvent(poll);
            applicationEventPublisher.publishEvent(pollEndedEvent);
        }
    }
}
