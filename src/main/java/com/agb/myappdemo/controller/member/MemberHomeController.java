package com.agb.myappdemo.controller.member;

import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.UserDao;
import com.agb.myappdemo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class MemberHomeController {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MemberHomeController(UserServiceImpl userServiceImpl, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/member/home")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        // Extract the first role and remove "ROLE_" prefix
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER") // Default role if none exists
                .replace("ROLE_", ""); // Remove "ROLE_" prefix

        model.addAttribute("userRole", role); // "ADMIN" or "USER"

        return "memberPage";
    }

    @PostMapping("/updatePassword")
    public String updateUserPassword(Model model,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     RedirectAttributes redirectAttributes) {

        model.addAttribute("oldPassword",userDetails.getPassword());

        model.addAttribute("error",false);

        model.addAttribute("success",false);

        User user = userServiceImpl.findByUsername(userDetails.getUsername());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error",
                    "Old password does not match.");
            return "redirect:/member/home";
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error",
                    "New password cannot be the same as the old password.");
            return "redirect:/member/home";

        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error",
                    "New password does not match.");
            return "redirect:/member/home";
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userServiceImpl.saveUser(user);

        redirectAttributes.addFlashAttribute("success",
                "Password updated successfully.");

        return "redirect:/member/home";
    }

}
