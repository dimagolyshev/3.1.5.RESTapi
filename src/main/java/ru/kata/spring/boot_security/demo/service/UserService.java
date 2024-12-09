package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(String name, String password, Byte age, String email, List<String> roles);

    List<User> list();

    User edit(Long id, String name, String password, Byte age, String email, List<String> roles);

    void deleteById(Long id);

    User findByName(String name);

    List<String> getUserDetails(User user);

}
