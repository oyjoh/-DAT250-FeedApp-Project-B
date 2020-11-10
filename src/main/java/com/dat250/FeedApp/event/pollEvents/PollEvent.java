package com.dat250.FeedApp.event.pollEvents;

import org.springframework.context.ApplicationEvent;

public class PollEvent extends ApplicationEvent {

    String description;

    public PollEvent(Object source, String description) {
        super(source);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
