package com.dat250.FeedApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Poll extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    private String summary;

    private Date endAt;

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

    @JsonIgnore
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries;


    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    @JsonIgnore
    public JsonObject getResult(){
        JsonObject result = new JsonObject();
        int sumYes = 0, sumNo = 0;
        for(Entry entry : entries){
            if(entry.getValue() == Value.NO){
                sumNo += entry.getNumber();
            } else {
                sumYes += entry.getNumber();
            }
        }
        result.addProperty("yes", sumYes);
        result.addProperty("no", sumNo);
        return result;
    }

    protected Poll() {
    }
}
