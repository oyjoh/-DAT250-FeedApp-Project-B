package com.dat250.FeedApp.event.pollEvents;

public class PollEndedEvent extends PollEvent{

    public PollEndedEvent(Object source) {
        super(source, "ended");
    }
}
