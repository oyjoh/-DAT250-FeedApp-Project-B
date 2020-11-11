package com.dat250.FeedApp.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Poll extends AuditModel implements Comparable<Poll> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;

    private String summary;

    private Date endAt;
    private Boolean ended;

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
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Entry> entries;


    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public long getYes(){
        return entries == null ? 0 : entries.stream().filter(entry -> entry.getValue() == Value.YES).count();
    }

    public long getNo(){
        return entries == null ? 0 : entries.stream().filter(entry -> entry.getValue() == Value.NO).count();
    }

    protected Poll() {
    }

    @Override
    public int compareTo(@NotNull Poll o) {
        return endAt.compareTo(o.getEndAt());
    }
}

