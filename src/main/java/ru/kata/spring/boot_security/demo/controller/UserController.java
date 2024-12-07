package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        List<String> details = new ArrayList<>();
        details.add("Username: " + user.getName());
        details.add("Age: " + user.getAge());
        details.add("E-mail: " + user.getEmail());

        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Role role: user.getRoles()) {
            sj.add(role.getName());
        }

        details.add("Roles: " + sj);
        model.addAttribute("details", details);
        return "user";
    }

}