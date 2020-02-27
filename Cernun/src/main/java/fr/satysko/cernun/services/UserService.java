package fr.satysko.cernun.services;

import fr.satysko.cernun.daos.UserDao;
import fr.satysko.cernun.interfaces.GenericServices;
import fr.satysko.cernun.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements GenericServices<User> {

    //@Autowired
    UserDao dao;

    @Override
    public List<User> findAll() {
        return null;
    }

    public List<User> findAll(int id) {
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
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {

    }

    public User connect(User u) {
        return null;
    }
}
