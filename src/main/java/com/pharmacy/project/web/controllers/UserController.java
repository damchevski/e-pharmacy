package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.Order;
import com.pharmacy.project.model.User;
import com.pharmacy.project.model.exceptions.InvalidOldPassword;
import com.pharmacy.project.model.exceptions.InvalidUserCredentialsException;
import com.pharmacy.project.service.ManufacturerService;
import com.pharmacy.project.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ManufacturerService manufacturerService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ManufacturerService manufacturerService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.manufacturerService = manufacturerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model, HttpServletRequest request) {
        model.addAttribute("user", this.userService.loadUserByUsername(request.getRemoteUser()));
        model.addAttribute("top", this.manufacturerService.getTop());
        return "user/userProfile.html";
    }

    @GetMapping("/myOrders")
    public String getUserOrders(Model model,
                                HttpServletRequest request) {
        List<Order> userOrders = this.userService.userOrders(request.getRemoteUser());
        if (userOrders.size() != 0) {
            model.addAttribute("firstOrder", userOrders.get(0));
            userOrders.remove(0);
        } else model.addAttribute("firstOrder", null);

        model.addAttribute("orders", userOrders);
        model.addAttribute("top", this.manufacturerService.getTop());
        return "user/userOrders.html";
    }

    @GetMapping("/addCredits")
    public String getAddCreditsPage(Model model,
                                    HttpServletRequest request) {
        model.addAttribute("user", this.userService.loadUserByUsername(request.getRemoteUser()));
        model.addAttribute("top", this.manufacturerService.getTop());
        return "user/addCredits.html";
    }

    @PostMapping("/addCredits")
    public String addCredits(@RequestParam String username,
                             @RequestParam Integer credits) {
        this.userService.addCredits(username, credits);

        return "redirect:/user/profile";
    }

}
