package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
