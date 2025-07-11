package com.agb.myappdemo.controller.address;

import com.agb.myappdemo.dto.DivisionDto;
import com.agb.myappdemo.dto.TownshipDto;
import com.agb.myappdemo.dto.UserDto;
import com.agb.myappdemo.entity.Division;
import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.DivisionDao;
import com.agb.myappdemo.repository.TownshipDao;
import com.agb.myappdemo.service.LocationService;
import com.agb.myappdemo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final UserServiceImpl userServiceImpl;
    private final TownshipDao townshipDao;
    private final DivisionDao divisionDao;

    @GetMapping("/township")
    String viewTownship(@RequestParam(defaultValue = "")String search,
                        @RequestParam(defaultValue = "0")int page,
                         @RequestParam(defaultValue = "70")int size,
                         Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));

        Page<Township> pageTownship ;

        if (search != null && !search.isEmpty()){
            pageTownship = townshipDao.findAll(pageable);
        }else {
            pageTownship = townshipDao.findTownshipByNameContainingIgnoreCase(search,pageable);
        }

        model.addAttribute("townships" , pageTownship.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", pageTownship.getTotalPages());


//        List<Township> townships = locationService.getAllTownship();
//        model.addAttribute("townships" , townships);
        return "township";
    }

    @GetMapping("/division")
    String viewDivision(@RequestParam(defaultValue = "")String search,
                        @RequestParam(defaultValue = "0")int page,
                        @RequestParam(defaultValue = "50")int size,
                        Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"name"));

        Page<Division> pageDivision;

        if (search != null && !search.isEmpty()){
            pageDivision = divisionDao.findAll(pageable);
        }else {
            pageDivision = divisionDao.findDivisionsByNameContainingIgnoreCase(search, pageable);
        }

        model.addAttribute("divisions" , pageDivision.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", pageDivision.getTotalPages());

//        List<Division> divisions = locationService.getAllDivision();
//        model.addAttribute("divisions", divisions);
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
