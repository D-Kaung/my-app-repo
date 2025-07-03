package com.agb.myappdemo.controller.App;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyAppController {

    @GetMapping("/app")
    String myApp () {
        return "myApp";
    }


}
