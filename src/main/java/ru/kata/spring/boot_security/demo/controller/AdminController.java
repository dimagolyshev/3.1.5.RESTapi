package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.service.ViewFormatter;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final ViewFormatter viewFormatter;

    public AdminController(UserService userService, RoleService roleService, ViewFormatter viewFormatter) {
        this.userService = userService;
        this.roleService = roleService;
        this.viewFormatter = viewFormatter;
    }

    @GetMapping(value = "/admin")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.list());
        model.addAttribute("availableRoles", roleService.list());
        model.addAttribute("defaultRoles", roleService.getDefaultRoles());
        model.addAttribute("viewFormatter", viewFormatter);
        return "admin";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@RequestParam String name,
                          @RequestParam String password,
                          @RequestParam byte age,
                          @RequestParam String email,
                          @RequestParam List<String> roles) {
        userService.add(name, password, age, email, roles);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/edit")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String password,
                             @RequestParam byte age,
                             @RequestParam String email,
                             @RequestParam List<String> roles) {
        userService.edit(id, name, password, age, email, roles);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}