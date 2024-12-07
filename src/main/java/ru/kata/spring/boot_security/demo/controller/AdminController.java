package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.service.UserViewFormatter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserViewFormatter userViewFormatter; // Formatter to print all the user roles in one row

    public AdminController(UserService userService, RoleService roleService, UserViewFormatter userViewFormatter) {
        this.userService = userService;
        this.roleService = roleService;
        this.userViewFormatter = userViewFormatter;
    }

    @GetMapping(value = "/admin")
    public String printUsers(ModelMap model) {
        List<User> users = userService.list();
        List<Role> availableRoles = roleService.list();
        List<String> defaultRoles = roleService.getDefaultRoles();
        model.addAttribute("users", users);
        model.addAttribute("availableRoles", availableRoles);
        model.addAttribute("defaultRoles", defaultRoles);
        model.addAttribute("userViewFormatter", userViewFormatter);
        return "admin";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@RequestParam String name,
                          @RequestParam String password,
                          @RequestParam byte age,
                          @RequestParam String email,
                          @RequestParam List<String> roles) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setAge(age);
        user.setEmail(email);
        Set<Role> userRoles = roles.stream().map(roleService::findByName).collect(Collectors.toSet());
        user.setRoles(userRoles);
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/edit")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String password,
                             @RequestParam byte age,
                             @RequestParam String email,
                             @RequestParam List<String> roles) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(password);
        user.setAge(age);
        user.setEmail(email);
        Set<Role> userRoles = roles.stream().map(roleService::findByName).collect(Collectors.toSet());
        user.setRoles(userRoles);
        userService.edit(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}