package com.dat250.FeedApp.event.pollEvents;

public class PollCreatedEvent extends PollEvent{

    public PollCreatedEvent(Object source) {
        super(source, "created");
    }
}
