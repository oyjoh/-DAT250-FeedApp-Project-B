package com.dat250.FeedApp.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
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

    protected Poll() {}
}
