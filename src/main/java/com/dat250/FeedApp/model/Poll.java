package com.dat250.FeedApp.model;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private Person person;

    @Override
    public String toString() {
        return "Poll{" +
                "pollId=" + pollId +
                ", summary='" + summary + '\'' +
                ", joinKey='" + joinKey + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    protected Poll() {}
}
