package com.javarush.javarushfinal.controller;

import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show-login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/show-register-page")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register-new-user")
    public String processRegistration(@Valid @ModelAttribute User user, BindingResult result) {

        userService.checkUserName(user, result);

        if (result.hasErrors()) {
            return "/register";
        }
        userService.registerUser(user);
        return "/login";
    }

}
