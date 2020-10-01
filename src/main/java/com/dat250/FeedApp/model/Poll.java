package com.dat250.FeedApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Poll extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    private String summary;
    private String joinKey;
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @OneToMany(mappedBy = "poll", orphanRemoval = true)
    private List<Entry> entries = new ArrayList<>();

    protected Poll() {}
}
