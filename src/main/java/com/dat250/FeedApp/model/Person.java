package com.dat250.FeedApp.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Person extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(unique=true)
    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String hash;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "personId"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pollId")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Poll> polls;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "entryId")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Entry> entries;

    public boolean isAdmin(){ return roles.contains(Role.ADMIN); }
    public boolean isUser(){ return roles.contains(Role.USER); }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hash='" + hash + '\'' +
                ", roles=" + roles +
                ", polls=" + polls +
                ", entries=" + entries +
                '}';
    }

    public Person() {}
}
