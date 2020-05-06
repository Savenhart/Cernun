package fr.satysko.cernun.controllers;

import com.google.gson.Gson;
import fr.satysko.cernun.models.Creature;
import fr.satysko.cernun.models.User;
import fr.satysko.cernun.models.UserWorld;
import fr.satysko.cernun.models.World;
import fr.satysko.cernun.repositories.CreatureRepository;
import fr.satysko.cernun.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SimulationController {

    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    @MessageMapping("/connect/{worldID}")
    public void connect(@Payload String message, @PathParam("worldID") int worldID) throws Exception{
        User user = new Gson().fromJson(message, User.class);
        World world = worldRepository.findById(worldID).orElse(null);
        users.add(user);
        UserWorld userWorld = new UserWorld();
        userWorld.setWorld(world);
        userWorld.setUser(user);
        List<Creature> lstCreatures = creatureRepository.findAllByWorldAndUser(worldID, user.getId());
        if (creatures == null || creatures.size() == 0){
            int cX = (int) Math.floor(Math.random() * 25);
            int cY = (int) Math.floor(Math.random() * 25);
            lstCreatures.add(new Creature(
                    cX,
                    cY,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX,
                    cY + 1,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX + 1,
                    cY + 1,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX + 1,
                    cY,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX,
                    cY - 1,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX - 1,
                    cY,
                    userWorld
            ));
            lstCreatures.add(new Creature(
                    cX - 1,
                    cY + 1,
                    userWorld
            ));
        }
        creatures.addAll(lstCreatures);
    }

    @MessageMapping("/disconnect/{worldID}")
    public void deconnect(@Payload String message, @PathParam("worldID") int worldID) throws Exception{
        User user = new Gson().fromJson(message, User.class);
        users.remove(user);
        List<Creature> lstCreatures = creatureRepository.findAllByWorldAndUser(worldID, user.getId());
        creatures.removeAll(lstCreatures);
    }

    public void loop() throws InterruptedException {
        while(true){
            for (Creature c: creatures) {
                c.update();
            }
            sendingOperations.convertAndSend("/creature", creatures);
            Thread.sleep(1000);
        }
    }

}
