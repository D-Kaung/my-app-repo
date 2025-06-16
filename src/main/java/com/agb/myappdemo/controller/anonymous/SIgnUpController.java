package com.agb.myappdemo.controller.anonymous;

import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.DivisionDao;
import com.agb.myappdemo.repository.TownshipDao;
import com.agb.myappdemo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signUp")
public class SIgnUpController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public SIgnUpController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping
    public String processSignUp(@Valid @ModelAttribute("user") User user,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
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


