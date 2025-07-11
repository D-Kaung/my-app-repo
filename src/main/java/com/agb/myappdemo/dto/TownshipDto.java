package com.agb.myappdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TownshipDto {

    private Long id;
    private String name;
    private Long divisionId;

}
