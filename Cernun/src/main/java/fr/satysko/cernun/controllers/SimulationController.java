package fr.satysko.cernun.controllers;

import com.google.gson.Gson;
import fr.satysko.cernun.models.Creature;
import fr.satysko.cernun.models.User;
import fr.satysko.cernun.repositories.CreatureRepository;
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
    private SimpMessageSendingOperations sendingOperations;

    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    @MessageMapping("/connect/{worldID}")
    public void connect(@Payload String message, @PathParam("worldID") int worldID) throws Exception{
        User user = new Gson().fromJson(message, User.class);
        users.add(user);
        List<Creature> lstCreatures = creatureRepository.findAllByWorldAndUser(worldID, user.getId());
        if (creatures == null || creatures.size() == 0){
            for (int i = 0; i < 5; i++) {
                Creature c = new Creature(
                    
                );
            }
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
            sendingOperations.convertAndSend("/creature", creatures);
            Thread.sleep(1000);
        }
    }

}
