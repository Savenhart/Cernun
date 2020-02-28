package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountNameAndPassword(String accountName, String password);

    List<User> findByUserNameOrAccountName(String userName, String accountName);
}
