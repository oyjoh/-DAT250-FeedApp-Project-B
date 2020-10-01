package com.dat250.FeedApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Entry extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @Enumerated(EnumType.STRING)
    private Value value;
    private Integer number;

    @ManyToOne(fetch = FetchType.EAGER)
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    protected Entry() {}
}