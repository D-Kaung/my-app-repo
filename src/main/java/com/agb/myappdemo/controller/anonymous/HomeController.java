package com.agb.myappdemo.controller.anonymous;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({
        "/",
         "/signIn"
            })
public class HomeController {

    @GetMapping
    String index() {
        return "";
    }
}
