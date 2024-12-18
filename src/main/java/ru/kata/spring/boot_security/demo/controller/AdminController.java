package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public ModelAndView printUsers() {
        return new ModelAndView("admin");
    }

}