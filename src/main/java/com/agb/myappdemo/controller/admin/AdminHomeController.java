package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.UserDao;
import com.agb.myappdemo.service.LocationService;
import com.agb.myappdemo.service.UserService;
import com.agb.myappdemo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {

    private final UserDao userDao;
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final LocationService locationService;

    @GetMapping("/admin/home")
    public String adminPage(@RequestParam(name = "search", defaultValue = "",
            required = false) String search,
                       @AuthenticationPrincipal UserDetails userDetails,
                       Model model) {

        if (userDetails == null) {
            return "redirect:/signIn";
        }

        int page = 0;
        int size = 170;

        boolean isAdmin = userDetails.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        List<User> currentUser = userDao.findUserByUsername(userDetails.getUsername());
        if (!currentUser.isEmpty()) {
            model.addAttribute("usersName", currentUser.get(0).getUsername());
            model.addAttribute("usersRoles", currentUser.get(0).getRole());
        }

        if (isAdmin) {

//            Sort sort = ascending ? Sort.by("username").ascending() : Sort.by("phone").descending();
            Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());

            Page<User> pagedUsers;

            if (search != null && !search.isEmpty()) {
                pagedUsers = userDao.findByUsernameContainingIgnoreCase(search, pageable);
            } else {
                pagedUsers = userDao.findAll(pageable);
            }

            model.addAttribute("usersList", pagedUsers.getContent());
            model.addAttribute("userCount", pagedUsers.getTotalElements());
            model.addAttribute("totalPages", pagedUsers.getTotalPages());
            model.addAttribute("currentPage", pagedUsers.getNumber());
            model.addAttribute("ADMIN", true);
            model.addAttribute("divisions", locationService.getAllDivision());

            return "adminPage";
        }

        return "memberPage";
    }

    @PostMapping("/admin/updatePassword")
    public String updatePassword(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam("oldPassword")String oldPassword,
                                 @RequestParam("newPassword")String newPassword,
                                 @RequestParam("confirmPassword")String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        model.addAttribute("oldPassword", userDetails.getPassword());

        model.addAttribute("error", false);

        model.addAttribute("success", false);

        User user = userServiceImpl.findByUsername(userDetails.getUsername());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())){
           redirectAttributes.addFlashAttribute("error", "Old password does not match");
            return "redirect:/admin/home";
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())){
           redirectAttributes.addFlashAttribute("error", "New password does not same with the old password");
            return "redirect:/admin/home";
        }
        if (!newPassword.equals(confirmPassword)){
          redirectAttributes.addFlashAttribute("error", "New password does match");
            return "redirect:/admin/home";
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userServiceImpl.saveUser(user);

      redirectAttributes.addFlashAttribute("success", "Finally done update password");

        return "redirect:/admin/home";

    }
}
