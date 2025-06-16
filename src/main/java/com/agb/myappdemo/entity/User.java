package com.agb.myappdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Phone number cannot be empty!")
    @Pattern(regexp = "\\d+",message = "phone number must be only digits!")
    private Integer phone;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "nrc cannot be empty!")
    @Pattern(regexp = "^\\d{1,2}/[A-Za-z]{2,7}\\(N\\)\\d{6}$",
             message = "NRC must be like nrc format(e.g 5/abc(N)123456)!")
    private String nrc;

    @Column(nullable = false)
    private Date LocalDate;

    private String Address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "township_id", nullable = false)
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
