package com.agb.myappdemo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MemberManagementController {

    @GetMapping("/admin/member")
    String index() {
        return "";
    }
}
