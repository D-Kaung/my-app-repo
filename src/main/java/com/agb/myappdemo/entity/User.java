package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
