package com.agb.myappdemo.controller.anonymous;

import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.DivisionDao;
import com.agb.myappdemo.service.LocationService;
import com.agb.myappdemo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signUp")
@RequiredArgsConstructor
public class SignUpController {

    private final UserServiceImpl userServiceImpl;
    private final DivisionDao divisionDao;
    private final LocationService locationService;

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("divisions", locationService.getAllDivision() );
        return "signUp";
    }

    @PostMapping
    public String processSignUp(@Valid @ModelAttribute("user") User user,
                                BindingResult result, HttpSession session,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("divisions", divisionDao.findAll());
            return "signUp";
        }

        try {
            userServiceImpl.signUpUser(user);
            session.setAttribute("userName", user.getUsername());
            return "redirect:/";  // Changed from "/signUp/success" to "/"
        } catch (Exception e) {
             if (e.getMessage().contains("nrc")) {
             model.addAttribute("nrcError", e.getMessage());
               } else if (e.getMessage().contains("Phone")) {
                   model.addAttribute("phoneError", e.getMessage());
               } else {
                 model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
        }
                model.addAttribute("divisions", divisionDao.findAll());
               return "signUp";
    }

}
}


