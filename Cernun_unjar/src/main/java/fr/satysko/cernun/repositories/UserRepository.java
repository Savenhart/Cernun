package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    List<User> findByUserNameOrEmail(String userName, String email);

    @Query("SELECT CASE WHEN (count(u) = 0) THEN true ELSE false END FROM User u WHERE u.userName = ?1 OR u.email = ?1")
    Boolean verifyName(String name);

    @Query("SELECT u FROM User u JOIN u.userWorlds uw WHERE uw.world.id = ?1")
    List<User> findAllByWorld(int id);
}
