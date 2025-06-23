package com.agb.myappdemo.controller.address;

import com.agb.myappdemo.dto.DivisionDto;
import com.agb.myappdemo.dto.TownshipDto;
import com.agb.myappdemo.dto.UserDto;
import com.agb.myappdemo.entity.Division;
import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.service.LocationService;
import com.agb.myappdemo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class LocationController {

   private final LocationService locationService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public LocationController(LocationService locationService, UserServiceImpl userServiceImpl) {
        this.locationService = locationService;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/township")
    String viewTownship(Model model) {
        List<Township> townships = locationService.getAllTownship();
        model.addAttribute("townships" , townships);
        return "township";
    }

    @GetMapping("/division")
    String viewDivision(Model model) {
        List<Division> divisions = locationService.getAllDivision();
        model.addAttribute("divisions", divisions);
        return "division";
    }

//    @GetMapping("/divisions")
//    @ResponseBody
//    public  List<DivisionDto>getDivisionById(@RequestParam("divisionId")Long divisionId){
//        return locationService.getAllDivisionById(divisionId)
//                .stream()
//                .map(d ->  new DivisionDto(d.getId(), d.getName()))
//                .collect(Collectors.toList());
//
//
//    }
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
                , u.getAddress(), u.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/users/divisions")
    public List<UserDto> getUserByDivisionId(@RequestParam("divisionId")Long divisionId) {

        List<User> users = locationService.getAllUserByDivisionId(divisionId);

        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPhone(), u.getNrc(),
                        u.getAddress(), u.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    @PostMapping("/update/divisionStatus")
    public String updateStatus(@RequestParam(value = "newStatus", required = false)Status newStatus,
                               @RequestParam("divisionId")Long divisionId,
                               RedirectAttributes redirectAttributes) {

        Division division = locationService.findDivisionId(divisionId);

        if (division == null ){
            redirectAttributes.addFlashAttribute("error", "There is no division.");
            return "redirect:/division";
        }

        if (newStatus != null) division.setStatus(newStatus);
        locationService.saveDivision(division);
        return "redirect:/division";
    }

    @PostMapping("/update/townshipStatus")
    public String updateStatus(@RequestParam("townshipId")Long townshipId,
                               @RequestParam(value = "newStatus",required = false)Status newStatus,
                               RedirectAttributes redirectAttributes){

        Township township = locationService.findTownshipId(townshipId);

        if (township == null) {
            redirectAttributes.addFlashAttribute("error", "There is no township.");
            return "redirect:/township";
        }
         if (newStatus != null) township.setStatus(newStatus);
         locationService.saveTownship(township);
         return "redirect:/township";
    }

}
