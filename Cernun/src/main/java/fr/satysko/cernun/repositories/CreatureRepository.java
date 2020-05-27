package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreatureRepository extends JpaRepository<Creature, Integer> {

    @Query("SELECT c FROM Creature c JOIN c.userWorld uw WHERE uw.world.id = ?1 AND uw.user.id = ?2")
    List<Creature> findAllByWorldAndUser(int worldId, int userId);
}
