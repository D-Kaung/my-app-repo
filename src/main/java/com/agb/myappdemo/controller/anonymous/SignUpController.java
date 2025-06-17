package com.agb.myappdemo.controller.anonymous;

import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.DivisionDao;
import com.agb.myappdemo.service.UserServiceImpl;
import jakarta.validation.Valid;
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
public class SignUpController {

    private final UserServiceImpl userServiceImpl;
    private final DivisionDao divisionDao;

    @Autowired
    public SignUpController(UserServiceImpl userServiceImpl, DivisionDao divisionDao) {
        this.userServiceImpl = userServiceImpl;
        this.divisionDao = divisionDao;
    }

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("division", divisionDao.findAll());
        return "signUp";
    }

    @PostMapping
    public String processSignUp(@Valid @ModelAttribute("user") User user,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("division", divisionDao.findAll());
            return "signUp";
        }

        try {
            userServiceImpl.signUpUser(user);
            return "redirect:/";  // Changed from "/signUp/success" to "/"
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "signUp";
        }
    }
}


