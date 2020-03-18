package fr.satysko.cernun.controllers;

import fr.satysko.cernun.models.Biome;
import fr.satysko.cernun.models.RestResponse;
import fr.satysko.cernun.services.BiomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biome")
@CrossOrigin
public class BiomeController {

    @Autowired
    private BiomeService biomeService;

    //Liste tous les biomes
    @GetMapping({"", "/", "/all"})
    public RestResponse<List<Biome>> getAllBiomes(){
        RestResponse<List<Biome>> response = null;
        try{
            response = new RestResponse<>(biomeService.getAllBiomes());
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }

        return response;
    }

    //Retourne un biome par sa clef
    @GetMapping("/{key}")
    public RestResponse<Biome> findByKey(@PathVariable("key") String key){
        RestResponse<Biome> response = null;
        try{
            response = new RestResponse<>(biomeService.findByKey(key));
        }catch (IllegalArgumentException e){
            response = new RestResponse<>(e, 204);
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }

        return response;
    }

}
