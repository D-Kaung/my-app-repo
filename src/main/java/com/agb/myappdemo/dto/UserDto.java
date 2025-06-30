package com.agb.myappdemo.dto;

import com.agb.myappdemo.entity.Role;

import java.time.LocalDate;

public class UserDto {

    private int id;
    private String username;
    private String phone;
    private String nrc;
    private String address;
    private LocalDate dateOfBirth;
    private Role role;
    private double latitude;
    private double longitude;

    public UserDto(int id, String username, String phone, String nrc, String address, LocalDate dateOfBirth,
                   Role role, double latitude, double longitude) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.nrc = nrc;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
