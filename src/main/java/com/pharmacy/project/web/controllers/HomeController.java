package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.User;
import com.pharmacy.project.model.exceptions.InvalidArgumentsException;
import com.pharmacy.project.model.exceptions.InvalidUserCredentialsException;
import com.pharmacy.project.model.exceptions.PasswordsDoNotMatchException;
import com.pharmacy.project.model.exceptions.UsernameAlreadyExistsException;
import com.pharmacy.project.service.ManufacturerService;
import com.pharmacy.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ManufacturerService manufacturerService;
    private final UserService userService;

    public HomeController(ManufacturerService manufacturerService, UserService userService) {
        this.manufacturerService = manufacturerService;
        this.userService = userService;
    }

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("top", this.manufacturerService.getTop());
        return "home/home.html";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error,
                           HttpServletRequest request,
                           Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("top", this.manufacturerService.getTop());
        return "home/login.html";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) String error,
                                  Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("top", this.manufacturerService.getTop());
        return "home/register.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname) {
        try {
            this.userService.register(username, password, repeatedPassword, name, surname);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    @GetMapping("/noAccess")
    public String noAccessPage() {
        return "home/accessDenied.html";
    }


}
