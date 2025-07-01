package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.entity.Role;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.UserDao;
import com.agb.myappdemo.service.UserService;
import com.agb.myappdemo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MemberManagement {

    private final UserDao userDao;
    private final UserServiceImpl userServiceImpl;
    private final UserService userService;

    @Autowired
    public MemberManagement(UserDao userDao, UserServiceImpl userServiceImpl,
                            UserService userService){
        this.userDao = userDao;
        this.userServiceImpl = userServiceImpl;
        this.userService = userService;
    }

    @PostMapping("/delete")
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("id") int id,
                             RedirectAttributes redirectAttributes
    ) {

        boolean isAdmin = userDetails.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin){
            redirectAttributes.addFlashAttribute("error", "unauthorized to delete user");
            return "redirect:/admin/home?returnToUserList=true";
        }

        User currentUser = userDao.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("CurrentUser not found"));
        if (currentUser.getId() == id){
            redirectAttributes.addFlashAttribute("error", "Cannot delete yourself");
            return "redirect:/admin/home?returnToUserList=true";
        }
        try{
            System.out.println("Attempting to delete user with ID: " + id);
            Optional<User> optionalUser = userDao.findById(id);
            System.out.println("Found user: " + optionalUser.orElse(null));

            if (!optionalUser.isPresent()){
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/admin/home?returnToUserList=true";
            }

            userServiceImpl.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

        }
        return "redirect:/admin/home?returnToUserList=true";
    }

    @PatchMapping("/updateUser")
    public String updateUser(
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "newAddress", required = false) String newAddress,
            @RequestParam(value = "newPhone", required = false) String newPhone,
            @RequestParam(value = "newUsername", required = false) String newUsername,
            @RequestParam(value = "newNrc", required = false) String newNrc,
            @RequestParam(value = "newLatitude", required = false)double newLatitude,
            @RequestParam(value = "newLongitude", required = false)double newLongitude,
            @RequestParam(value = "newRole", required = false) Role newRole,
            @RequestParam(value = "returnToUserList", required = false) String returnToUserList,
            @RequestParam(value = "editedUserId", required = false) String editedUserId,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        // Store current form values in flash attributes for error cases
        redirectAttributes.addFlashAttribute("currentUsername", newUsername);
        redirectAttributes.addFlashAttribute("currentPhone", newPhone);
        redirectAttributes.addFlashAttribute("currentNrc", newNrc);
        redirectAttributes.addFlashAttribute("currentRole", newRole);
        redirectAttributes.addFlashAttribute("currentAddress", newAddress);
        redirectAttributes.addFlashAttribute("currentLatitude", newLatitude);
        redirectAttributes.addFlashAttribute("currentLongitude", newLongitude);
        // Find the user
        User user = userService.findUserById(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/admin/home";
        }

        // Check for duplicates
        if (newUsername != null && userService.existsByUsername(newUsername)
                && !newUsername.equals(user.getUsername())) {
            redirectAttributes.addFlashAttribute("usernameError", "This username already exists.");
            return "redirect:" + request.getHeader("Referer");
        }

        if (newPhone != null && userService.existsByPhone(newPhone)
                && !newPhone.equals(user.getPhone())) {
            redirectAttributes.addFlashAttribute("phoneError", "This phone number already exists.");
            return "redirect:" + request.getHeader("Referer");
        }

        if (newNrc != null && userService.existsByNrc(newNrc)
                && !newNrc.equals(user.getNrc())) {
            redirectAttributes.addFlashAttribute("nrcError", "This NRC already exists.");
            return "redirect:" + request.getHeader("Referer");
        }

        // Update fields
        if (newUsername != null) user.setUsername(newUsername);
        if (newNrc != null) user.setNrc(newNrc);
        if (newPhone != null) user.setPhone(newPhone);
        if (newRole != null) user.setRole(newRole);
        if (newAddress != null) user.setAddress(newAddress);
        user.setLatitude(newLatitude);
        user.setLongitude(newLongitude);

        try {
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("success", "User update is successful.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Update failed due to a database constraint.");
            return "redirect:" + request.getHeader("Referer");
        }

        // Redirect to user list with editedUserId
        if ("true".equals(returnToUserList)) {
            redirectAttributes.addAttribute("returnToUserList", true);
            redirectAttributes.addAttribute("editedUserId", userId); // Use userId directly
            return "redirect:/admin/home";
        }
        return "redirect:/admin/home";
    }

}
