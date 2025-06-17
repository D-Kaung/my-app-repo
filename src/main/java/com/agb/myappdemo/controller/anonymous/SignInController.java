package com.agb.myappdemo.controller.anonymous;


import com.agb.myappdemo.dto.UserDto;
import com.agb.myappdemo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignInController {

    @GetMapping
    String index(Model model) {
        model.addAttribute("user", new User());
        return "signIn";
    }

    @PostMapping
    String login() {
        return "";
    }
}
