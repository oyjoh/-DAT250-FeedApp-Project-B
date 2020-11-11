package com.dat250.FeedApp.event.pollEvents;

public class PollDeletedEvent extends PollEvent{

    public PollDeletedEvent(Object source) {
        super(source, "deleted");
    }
}
