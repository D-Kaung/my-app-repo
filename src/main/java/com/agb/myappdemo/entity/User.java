package com.agb.myappdemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public User(String username, String password, Integer phone, String nrc, Date localDate, String address) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.nrc = nrc;
        LocalDate = localDate;
        Address = address;
    }
}
