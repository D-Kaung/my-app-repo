package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Integer phone;
    private String nrc;
    private Date LocalDate;
    private String Address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "towship_id", nullable = false)
    private Township township;

    public User(String username, String password, String nrc,
                Integer phone, Date localDate, String address, Role role, Division division, Township township) {
        this.username = username;
        this.password = password;
        this.nrc = nrc;
        this.phone = phone;
        LocalDate = localDate;
        Address = address;
        this.role = role;
        this.division = division;
        this.township = township;
    }
}
