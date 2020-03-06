package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Integer> {
    @Query("SELECT c FROM Cell c WHERE c.world.id = ?1 AND c.location.posX = ?2 AND c.location.posY = ?3")
    Cell findPos(int w, int x, int y);

    @Query("SELECT CASE WHEN (count(c) > 0) THEN true ELSE false END FROM Cell c WHERE c.world.id = ?1 AND c.location.posX = ?2 AND c.location.posY = ?3")
    Boolean exist(int w, int x, int y);

    List<Cell> findAllByWorld_id(int id);
}
