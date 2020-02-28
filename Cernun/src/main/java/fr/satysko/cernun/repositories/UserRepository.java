package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountNameAndPassword(String accountName, String password);
}
