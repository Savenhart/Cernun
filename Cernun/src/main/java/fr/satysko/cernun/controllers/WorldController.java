package fr.satysko.cernun.controllers;

import fr.satysko.cernun.exceptions.WorldException;
import fr.satysko.cernun.models.Cell;
import fr.satysko.cernun.models.Location;
import fr.satysko.cernun.models.RestResponse;
import fr.satysko.cernun.models.World;
import fr.satysko.cernun.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/world")
@CrossOrigin
public class WorldController {

    @Autowired
    private WorldService worldService;

    //Liste tous les mondes
    @GetMapping({"", "/", "/all"})
    public RestResponse<List<World>> getAllWorld(){
        RestResponse<List<World>> response;
        try{
            List<World> worlds = worldService.findAll();
            if(worlds == null || worlds.size() == 0){
                response = new RestResponse<>(new WorldException("Aucun monde trouvés"), 204);
            }else {
                response = new RestResponse<>(worlds);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Liste tous les mondes d'un utilisateur défini
    @GetMapping({"/all/{id}"})
    public RestResponse<List<World>> getAllWorldUsers(@PathVariable("id") int id){
        RestResponse<List<World>> response;
        try{
            List<World> users = worldService.findAll(id);
            if(users == null || users.size() == 0){
                response = new RestResponse<>(new WorldException("Aucun monde trouvé pour l'utilisateur n°" + id), 204);
            }else {
                response = new RestResponse<>(users);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Retourne un monde par son id
    @GetMapping("/find/{id}")
    public RestResponse<World> findById(@PathVariable("id") int id){
        RestResponse<World> response;
        try {
            World world = worldService.find(id);
            if(world == null){
                response = new RestResponse<>(new WorldException("Le monde n°" + id + " n'existe pas"), 204);
            }else {
                response = new RestResponse<>(world);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Crée un monde
    @PostMapping("/create")
    public RestResponse<World> create(@RequestBody World w){
        RestResponse<World> response;
        try {
            World world = worldService.create(w);
            if (world == null) {
                response = new RestResponse<>(new WorldException("Le monde n'a pas pu être créé"), 204);
            } else {
                response = new RestResponse<>(world);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }


    //Met à jour un monde
    @PutMapping({"", "/"})
    public RestResponse<World> update(@RequestBody World w){
        RestResponse<World> response;
        try {
            World world = worldService.update(w);
                if (world == null) {
                    response = new RestResponse<>(new WorldException("Le monde n'existe pas"), 204);
                } else {
                    response = new RestResponse<>(world);
                }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Supprime un monde
    @DeleteMapping("/{id}")
    public RestResponse<String> delete(@PathVariable("id") int id){
        RestResponse<String> response;
        try {
            if(worldService.delete(id)){
                response = new RestResponse<>("Le monde a bien été supprimé");
            }else {
                response = new RestResponse<>(new WorldException("Le monde n'existe pas"), 204);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Retrouve toutes les cellules d'un monde
    @GetMapping("/grid/{id}")
    public RestResponse<List<Cell>> getGrid(@PathVariable("id") int id){
        RestResponse<List<Cell>> response = null;
        try {
            List<Cell> grid = worldService.findAllCell(id);
            if(grid.size() != 0){
                response = new RestResponse<>(grid);
            }else {
                response = new RestResponse<>(new WorldException("Le monde est vide"), 204);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Retrouve toutes les cellules d'un monde autour d'une position
    @PostMapping("/grid/{id}/{scale}")
    public RestResponse<List<Cell>> getGridLocation(@PathVariable("id") int id, @RequestBody Location pos, @PathVariable("scale") int scale){
        System.out.println(LocalDateTime.now() + "in");
        RestResponse<List<Cell>> response = null;
        try {
            List<Cell> grid = worldService.findAllCell(id, pos.getPosX(), pos.getPosY(), scale);
            response = new RestResponse<>(grid);
        }catch (WorldException e){
            response = new RestResponse<>(e, 204);
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
            throw e;
        }
        System.out.println(LocalDateTime.now() + " out");
        return response;
    }

}
