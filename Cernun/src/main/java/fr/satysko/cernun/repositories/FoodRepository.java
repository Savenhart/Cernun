package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("SELECT f FROM Food f WHERE f.world.id = ?1 AND f.location.posX = ?2 AND f.location.posY = ?3")
    Food findPos(int w, int x, int y);

    List<Food> findAllByWorld_id(int id);
}
