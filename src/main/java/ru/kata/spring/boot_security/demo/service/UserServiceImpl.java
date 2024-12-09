package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void add(String name, String password, Byte age, String email, List<String> roles) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setAge(age);
        user.setEmail(email);
        user.setRoles(roles
                .stream()
                .map(roleService::findByName)
                .collect(Collectors.toSet()));
        userDao.add(user);
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Transactional
    @Override
    public User edit(Long id, String name, String password, Byte age, String email, List<String> roles) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        else {
            user.setPassword(userDao.findById(id).getPassword());
        }
        user.setAge(age);
        user.setEmail(email);
        user.setRoles(roles
                .stream()
                .map(roleService::findByName)
                .collect(Collectors.toSet()));
        return userDao.edit(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<String> getUserDetails(User user) {
        return List.of(
                "Username: " + user.getUsername()
                , "Age: " + user.getAge()
                , "E-mail: " + user.getEmail()
                , "Roles: " + user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found in database", username));
        }
        return user;
    }

}

