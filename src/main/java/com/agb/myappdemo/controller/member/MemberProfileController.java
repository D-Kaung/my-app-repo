package com.agb.myappdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/profile")
public class MemberProfileController {

    @GetMapping
    String index() {
        return "";
    }
}
