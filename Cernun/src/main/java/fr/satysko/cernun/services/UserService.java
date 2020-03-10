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
        return repository.findAllByWorld(id);
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
        if(!repository.existsById(user.getId())){
            return null;
        }
        return repository.save(user);
    }

    @Override
    public boolean delete(int id) {
        if(!repository.existsById(id)){
            return false;
        }
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    public User connect(User u) {
        return repository.findByEmailAndPassword(u.getEmail(), u.getPassword());
    }

    public boolean verify(String name) {
        return repository.verifyName(name);
    }
}
