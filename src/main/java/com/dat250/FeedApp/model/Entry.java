package com.dat250.FeedApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pollId")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "pollId_entry")
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "personId")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "personId_entry")
    private Person person;

    protected Entry() {}

    public static Entry simpleEntry(Entry entry){
        Entry newEntry = new Entry();
        newEntry.setValue(entry.getValue());
        newEntry.setCreatedAt(entry.getCreatedAt());
        newEntry.setNumber(entry.getNumber());
        return newEntry;
    }

    public static Entry from(Entry entry, Person person, Poll poll) {
        Entry newEntry = new Entry();
        newEntry.setPoll(poll);
        newEntry.setPerson(person);
        newEntry.setValue(entry.getValue());
        newEntry.setNumber(entry.getNumber());
        return newEntry;
    }

    @Override
    public String toString() {
        return "{" +
                "\"entryId\": " + entryId + "," +
                "\"value\": \"" + value + "\"," +
                "\"number\": " + number + "," +
                "\"poll\": " + poll + "," +
                "\"person\":" + person +
                '}';
    }
}