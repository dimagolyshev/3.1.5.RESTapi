package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> list() {
        return roleDao.list();
    }

    @Override
    public List<String> getDefaultRoles() {
        return Stream.of(roleDao.findByName("ROLE_USER")).map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
