package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.UserWorld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserWorldRepository extends JpaRepository<UserWorld, Integer> {
    @Query("SELECT uw FROM UserWorld uw WHERE uw.world.id = ?1 AND uw.user.id = ?2")
    UserWorld findByWorldAndUser(int worldId, int userId);
}
