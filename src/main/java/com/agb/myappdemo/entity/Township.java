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
public class Township {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;



    public Township(String name, Status status, Division division, List<User> users) {
        this.name = name;
        this.status = status;
        this.division = division;
        this.users = users;
    }
}