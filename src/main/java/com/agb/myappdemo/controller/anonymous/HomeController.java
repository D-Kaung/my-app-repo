package com.agb.myappdemo.controller.anonymous;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            userName = "Guest";
        }
        model.addAttribute("userName", userName);
        return "home"; // home.html
    }

}

