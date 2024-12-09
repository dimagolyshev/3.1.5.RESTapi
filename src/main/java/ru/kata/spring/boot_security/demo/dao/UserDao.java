package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> list();

    User edit(User user);

    void deleteById(Long id);

    User findById(Long id);

    User findByName(String name);

}
