package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Username cannot be empty and Please enter!!")
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Password cannot be empty and Please enter!!")
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\d+", message = "Phone number must be only digits and Cannot be empty!!")
    private String phone;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{1,2}/[A-Za-z]{2,7}\\(N\\)\\d{6}$",
            message = "NRC must be in format (e.g., 5/ABC(N)123456) and Cannot be empty!!")
    private String nrc;

    @Column(nullable = false, name = "date_of_birth")
    @NotNull(message = "DateOfBirth cannot be empty and Please enter!!")
    private LocalDate dateOfBirth;

    private String address;

    @NotNull(message = "Pls select your latitude.")
    private double latitude;

    @NotNull(message = "Pls select your longitude.")
    private double longitude;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Valid
    @NotNull(message = "Pls choice your division.")
    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @Valid
    @NotNull(message = "Pls choice your township.")
    @ManyToOne
    @JoinColumn(name = "township_id", nullable = false)
    private Township township;



    public User(int id, String username, String password, String phone, String nrc,
                LocalDate dateOfBirth, String address,
                double latitude, double longitude,
                Role role, Division division, Township township) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.nrc = nrc;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = role;
        this.division = division;
        this.township = township;
    }

    public User() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone) {
        this.phone = phone;
    }

    public  String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Township getTownship() {
        return township;
    }

    public void setTownship(Township township) {
        this.township = township;
    }
}
