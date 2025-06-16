package com.agb.myappdemo.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signOut")
public class SignOutController {

    @GetMapping
    String idnex() {
        return "";
    }
}
