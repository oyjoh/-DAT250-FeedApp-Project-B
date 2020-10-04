package com.dat250.FeedApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String hash;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Poll> polls;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "personId"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public boolean isAdmin(){ return roles.contains(Role.ADMIN); }
    public boolean isUser(){ return roles.contains(Role.USER); }

    public String toString() {
        return "{" +
                "\"personId\": " + personId + "," +
                "\"name\": " + "\"" + name + "\"" +
                "}";
    }

    public Person() {}
}
