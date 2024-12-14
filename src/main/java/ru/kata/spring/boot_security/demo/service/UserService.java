package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    void add(String firstName, String lastName,  String password, Byte age, String email, List<String> roles);

    List<User> list();

    User edit(Long id, String firstName, String lastName, String password, Byte age, String email, List<String> roles);

    void deleteById(Long id);

    User findByUsername(String username);

    Map<String, String > getUserDetails(User user);

}
