package com.agb.myappdemo.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/home")
public class AdminHomeController {

    @GetMapping
    String index () {
        return "";
    }
}
