package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.entity.Role;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.UserDao;
import com.agb.myappdemo.service.UserService;
import com.agb.myappdemo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminHomeController {

    private final UserDao userDao;
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminHomeController(UserDao userDao,UserService userService, UserServiceImpl userServiceImpl) {
        this.userDao = userDao;
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
    }

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
            return "adminPage";
        }

        return "memberPage";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("id") int id,
                             RedirectAttributes redirectAttributes
    ) {

        boolean isAdmin = userDetails.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin){
            redirectAttributes.addFlashAttribute("error", "unauthorized to delete user");
            return "redirect:/home?returnToUserList=true";
        }

        User currentUser = userDao.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("CurrentUser not found"));
        if (currentUser.getId() == id){
            redirectAttributes.addFlashAttribute("error", "Cannot delete yourself");
            return "redirect:/home?returnToUserList=true";
        }
        try{
            System.out.println("Attempting to delete user with ID: " + id);
            Optional<User> optionalUser = userDao.findById(id);
            System.out.println("Found user: " + optionalUser.orElse(null));

            if (!optionalUser.isPresent()){
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/home?returnToUserList=true";
            }

            userServiceImpl.deleteUserById(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

        }
        return "redirect:/home?returnToUserList=true";
    }


    @PatchMapping("/users/update")
    public String updateUser(
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "newAddress", required = false) String newAddress,
            @RequestParam(value = "newPhone", required = false) String newPhone,
            @RequestParam(value = "newUsername", required = false) String newUsername,
            @RequestParam(value = "newNrc", required = false) String newNrc,
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

        // Find the user
        User user = userService.findUserById(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/home";
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
            return "redirect:/home";
        }
        return "redirect:/home";
    }

}
