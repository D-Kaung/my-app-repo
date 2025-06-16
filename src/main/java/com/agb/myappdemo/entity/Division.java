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

    @OneToMany
    List<Township> townships;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Division(String name) {
        this.name = name;
    }
}
