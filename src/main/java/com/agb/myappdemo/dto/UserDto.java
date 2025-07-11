package com.agb.myappdemo.dto;

import com.agb.myappdemo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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

}
