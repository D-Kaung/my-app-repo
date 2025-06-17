package com.agb.myappdemo.controller.address;

import com.agb.myappdemo.dto.TownshipDto;
import com.agb.myappdemo.dto.UserDto;
import com.agb.myappdemo.entity.Division;
import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LocationController {

   private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/township")
    String viewTownship(Model model) {
        List<Township> townships = locationService.getAllTownship();
        model.addAttribute("townships" , townships);
        return "township";
    }

    @GetMapping("division")
    String viewDivision(Model model) {
        List<Division> divisions = locationService.getAllDivision();
        model.addAttribute("divisions", divisions);
        return "division";
    }

    @ResponseBody
    @GetMapping("/townships")
    public List<TownshipDto> getTownshipsByDivisionId(@RequestParam("divisionId")Long divisionId){

      List<Township> townships = locationService.getAllTownshipByDivisionId(divisionId, Status.ACTIVE);

      return townships.stream()
              .map(t -> new TownshipDto(t.getId(), t.getName(), t.getDivision().getId()))
              .collect(Collectors.toList());

    }

    @ResponseBody
    @GetMapping("/users/townships")
    public List<UserDto> getUserByTownshipId(@RequestParam("townshipId")Long townshipId) {

        List<User> users = locationService.getAllUserByTownshipId(townshipId);

        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPhone(), u.getNrc()
                , u.getAddress(), u.getDivision().getId(), u.getTownship().getId()))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/users/divisions")
    public List<UserDto> getUserByDivisionId(@RequestParam("divisionId")Long divisionId) {

        List<User> users = locationService.getAllUserByDivisionId(divisionId);

        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPhone(), u.getNrc(),
                        u.getAddress(), u.getDivision().getId(),
                        u.getTownship().getId()))
                .collect(Collectors.toList());
    }

}
