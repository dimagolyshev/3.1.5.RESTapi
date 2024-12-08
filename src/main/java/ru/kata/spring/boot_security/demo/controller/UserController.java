package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.ViewFormatter;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;
    private final ViewFormatter viewFormatter;

    public UserController(UserService userService, ViewFormatter viewFormatter) {
        this.userService = userService;
        this.viewFormatter = viewFormatter;
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        List<String> userDetails = new ArrayList<>();
        userDetails.add("Username: " + user.getUsername());
        userDetails.add("Age: " + user.getAge());
        userDetails.add("E-mail: " + user.getEmail());
        userDetails.add("Roles: " + viewFormatter.formatUserRoles(user.getRoles()));
        model.addAttribute("userDetails", userDetails);
        return "user";
    }

}