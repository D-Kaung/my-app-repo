package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<Township> townships;

    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<User> users;


    public Division(String name, Status status, List<Township> townships, List<User> users) {
        this.name = name;
        this.status = status;
        this.townships = townships;
        this.users = users;
    }
}
