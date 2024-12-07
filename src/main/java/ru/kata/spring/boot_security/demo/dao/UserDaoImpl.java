package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> list() {
        return entityManager.createQuery("FROM User", User.class)
                .getResultList();
    }

    @Override
    public User edit(User user) {
        return (User) entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public User findByName(String name) {
        return entityManager.createQuery("FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

}
