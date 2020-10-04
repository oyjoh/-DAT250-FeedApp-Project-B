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
public class Poll extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    private String summary;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "joinkey_id", referencedColumnName = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "key")
    @JsonIdentityReference(alwaysAsId = true)
    private JoinKey joinKey;

    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "personId")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "personId_poll", nullable = false)
    private Person person;


    @Override
    public String toString() {
        return "{" +
                "\"created_at\": " + "\"" + getCreatedAt().toString() + "\"" + "," +
                "\"updated_at\": " + "\"" + getUpdatedAt().toString() + "\"" + "," +
                "\"pollId\": " + pollId + "," +
                "\"summary\": " + "\"" + summary + "\"" + "," +
                "\"joinKey\": " + "\"" + joinKey + "\"" + "," +
                "\"isPublic\": " + isPublic +
                "}";
    }

    protected Poll() {
    }
}
