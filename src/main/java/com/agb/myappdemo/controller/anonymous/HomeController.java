package com.agb.myappdemo.controller.anonymous;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(Model model, HttpSession session) {

            String userName = (String) session.getAttribute("username");
            model.addAttribute("userName", userName != null ? userName : "Guest");
            return "home";
        }
    }

