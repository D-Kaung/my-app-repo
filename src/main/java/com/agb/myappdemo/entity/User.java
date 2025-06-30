package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotBlank(message = "Username cannot be empty!! Pls enter your username.")
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Please enter password.Password cannot be empty!")
    private String password;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Phone number cannot be empty!")
    @Pattern(regexp = "\\d+", message = "Phone number must be only digits!")
    private String phone;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "NRC cannot be empty!")
    @Pattern(regexp = "^\\d{1,2}/[A-Za-z]{2,7}\\(N\\)\\d{6}$",
            message = "NRC must be in format (e.g., 5/ABC(N)123456)!")
    private String nrc;

    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    private double latitude;

    private double longitude;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "township_id", nullable = false)
    private Township township;

    public User(String username, String password, String phone, String nrc,
                LocalDate dateOfBirth, String address,
                double latitude, double longitude,
                Role role, Division division, Township township) {
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

  @NotEmpty(message = "Phone number cannot be empty!")
    @Pattern(regexp = "\\d+", message = "Phone number must be only digits!")
  public String getPhone() {
        return phone;
    }

    public void setPhone(@NotEmpty(message = "Phone number cannot be empty!") @Pattern(regexp = "\\d+", message = "Phone number must be only digits!") String phone) {
        this.phone = phone;
    }

    public @NotEmpty(message = "NRC cannot be empty!")
    @Pattern(regexp = "^\\d{1,2}/[A-Za-z]{2,7}\\(N\\)\\d{6}$",
            message = "NRC must be in format (e.g., 5/ABC(N)123456)!") String getNrc() {
        return nrc;
    }

    public void setNrc(@NotEmpty(message = "NRC cannot be empty!") @Pattern(regexp = "^\\d{1,2}/[A-Za-z]{2,7}\\(N\\)\\d{6}$",
            message = "NRC must be in format (e.g., 5/ABC(N)123456)!") String nrc) {
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
