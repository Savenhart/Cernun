package fr.satysko.cernun.services;

import fr.satysko.cernun.models.Biome;
import fr.satysko.cernun.utils.EBiome;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BiomeService {

    public List<Biome> getAllBiomes(){
        List<Biome> listeBiome = new ArrayList<>();
        for (EBiome b :
                EBiome.values()) {
            Biome biome = new Biome();
            biome.setBiome(b.getName());
            listeBiome.add(biome);
        }
        return listeBiome;
    }

    public Biome findByKey(String key){
        Biome b = new Biome();
        b.setBiome(key);
        return b;
    }

}
