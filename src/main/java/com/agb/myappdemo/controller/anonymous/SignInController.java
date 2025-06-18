package com.agb.myappdemo.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signIn")
public class SignInController {

    @GetMapping
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("logoutSuccess", "You have been logged out successfully.");
        }

        return "signIn";
    }
}