package fr.satysko.cernun.services;

import fr.satysko.cernun.exceptions.WorldException;
import fr.satysko.cernun.interfaces.GenericServices;
import fr.satysko.cernun.models.Cell;
import fr.satysko.cernun.models.World;
import fr.satysko.cernun.repositories.CellRepository;
import fr.satysko.cernun.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

@Service
public class WorldService implements GenericServices<World> {

    @Autowired
    WorldRepository repository;
    @Autowired
    CellRepository cRepository;


    @Override
    public List<World> findAll() {
        return repository.findAll();
    }
    public List<World> findAll(int id) {
        return repository.findAllByUser(id);
    }

    @Override
    public World find(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public World create(World world) {
        return repository.save(world);
    }

    @Override
    public World update(World world) {
        if(!repository.existsById(world.getId())){
            return null;
        }
        return repository.save(world);
    }

    @Override
    public boolean delete(int id) {
        if (!repository.existsById(id)){
            return false;
        }
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    public List<Cell> findAllCell(int id) {
        return cRepository.findAllByWorld_id(id);
    }

    public List<Cell> findAllCell(int id, int x, int y, int scale) throws WorldException {
        World world = repository.findById(id).orElse(null);
        if(world == null){
            throw new WorldException("Le monde n'existe pas");
        }
        List<Cell> grid = new ArrayList<>();
        for(int dy = -scale; dy <= scale; dy++){
            for(int dx = -scale; dx <= scale; dx++){
                // rectangle centrale
                if(abs(dy) < scale / 2
                    // Triangle inférieur
                    || (dy > 0 && (
                    // Pour les colonnes impaires, ne prends pas en compte la dernière ligne courante
                    (dx % 2 != 0 && dy < scale - abs(dx) / 2)
                        // Pour les colonnes paires, prends en compte la dernière ligne courante
                        || (dx % 2 == 0 && dy <= scale - abs(dx) / 2)))
                    // Triangle supérieur
                    || (dy < 0 && (
                    // Pour les colonnes impaires, ne prends pas en compte la dernière ligne suivante
                    (dx % 2 != 0 && -dy < scale - abs(dx) / 2 + 1)
                        // Pour les colonnes paires, prends en compte la dernière ligne courante
                        || (dx % 2 == 0 && -dy <= scale - abs(dx) / 2)))
                ){
                    int nx = x + dx;
                    int ny = y + dy;
                    Cell c = cRepository.findPos(id, nx, ny);
                    if(c == null){
                        c = cRepository.save(world.genCell(nx, ny));
                    }
                    grid.add(c);
                }
            }
        }
        return grid;
    }
}
