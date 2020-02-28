package fr.satysko.cernun.services;

import fr.satysko.cernun.interfaces.GenericServices;
import fr.satysko.cernun.models.User;
import fr.satysko.cernun.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements GenericServices<User> {

    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> findAll(int id) {
        return null;
    }

    @Override
    public User find(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public User connect(User u) {
        return repository.findByAccountNameAndPassword(u.getAccountName(), u.getPassword());
    }
}
