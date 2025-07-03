package com.agb.myappdemo.controller.address;

import com.agb.myappdemo.dto.TownshipDto;
import com.agb.myappdemo.dto.UserDto;
import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilterUsersAndTownshipsController {

    private final LocationService locationService;

    @Autowired
    public FilterUsersAndTownshipsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @ResponseBody
    @GetMapping("/townships")
    public List<TownshipDto> getTownshipsByDivisionId(@RequestParam("divisionId") Long divisionId) {
        return locationService.getAllTownshipByDivisionId(divisionId, Status.ACTIVE)
                .stream()
                .map(t -> new TownshipDto(t.getId(), t.getName(), t.getDivision().getId()))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/users/townships")
    public List<UserDto> getUserByTownshipId(@RequestParam("townshipId")Long townshipId) {

        List<User> users = locationService.getAllUserByTownshipId(townshipId);
        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPhone(), u.getNrc()
                        , u.getAddress(), u.getDateOfBirth(), u.getRole(), u.getLatitude(), u.getLongitude()))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/users/divisions")
    public List<UserDto> getUserByDivisionId(@RequestParam("divisionId")Long divisionId) {

        List<User> users = locationService.getAllUserByDivisionId(divisionId);

        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPhone(), u.getNrc(),
                        u.getAddress(), u.getDateOfBirth(), u.getRole(), u.getLatitude(), u.getLongitude()))
                .collect(Collectors.toList());
    }

}
