package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User add(User user);

    List<User> list();

    User edit(User user);

    void deleteById(Long id);

    User findByUsername(String username);

    Map<String, String > getUserDetails(User user);

}
