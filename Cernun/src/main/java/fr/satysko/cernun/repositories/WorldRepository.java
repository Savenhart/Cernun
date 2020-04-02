package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorldRepository extends JpaRepository<World, Integer> {

    @Query("SELECT w FROM World w JOIN w.userWorlds uw WHERE uw.user.id = ?1")
    List<World> findAllByUser(int id);
}
