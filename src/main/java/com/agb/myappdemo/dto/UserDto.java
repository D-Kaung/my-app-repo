package com.agb.myappdemo.dto;

public class UserDto {

    private int id;
    private String username;
    private String phone;
    private String nrc;
    private String address;
    private Long divisionId;
    private Long townshipId;

    public UserDto(int id, String username, String phone, String nrc, String address, Long divisionId,
                   Long townshipId) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.nrc = nrc;
        this.address = address;
        this.divisionId = divisionId;
        this.townshipId = townshipId;
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

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipId) {
        this.townshipId = townshipId;
    }
}
