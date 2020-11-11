package com.dat250.FeedApp.event.pollEvents;

public class PollUpdatedEvent extends PollEvent {

    public PollUpdatedEvent(Object source) {
        super(source, "updated");
    }
}
