package fr.satysko.cernun.services;

import fr.satysko.cernun.dao.UserDao;
import fr.satysko.cernun.interfaces.GenericServices;
import fr.satysko.cernun.models.User;

import java.util.List;

public class UserService implements GenericServices<User> {

    //@Autowired
    UserDao dao;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
