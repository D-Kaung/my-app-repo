package com.agb.myappdemo.controller.anonymous;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignOutController {

    @GetMapping("/signOut")
    String index(HttpSession session) {
        session.invalidate();
        return "home";
    }
}
