package com.agb.myappdemo.dto;

public class TownshipDto {

    private Long id;
    private String name;
    private Long divisionId;

    public TownshipDto(Long id, String name, Long divisionId) {
        this.id = id;
        this.name = name;
        this.divisionId = divisionId;
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

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }
}
