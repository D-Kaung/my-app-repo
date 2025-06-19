package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<Township> townships;

    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<User> users;


    public Division(String name,Double latitude,
                    Double longitude,
                    Status status, List<Township> townships, List<User> users) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.townships = townships;
        this.users = users;
    }

    public Division () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Township> getTownships() {
        return townships;
    }

    public void setTownships(List<Township> townships) {
        this.townships = townships;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
