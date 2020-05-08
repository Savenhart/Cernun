package fr.satysko.cernun.controllers;

import com.google.gson.Gson;
import fr.satysko.cernun.models.Creature;
import fr.satysko.cernun.models.User;
import fr.satysko.cernun.models.UserWorld;
import fr.satysko.cernun.models.World;
import fr.satysko.cernun.repositories.CreatureRepository;
import fr.satysko.cernun.repositories.UserRepository;
import fr.satysko.cernun.repositories.UserWorldRepository;
import fr.satysko.cernun.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SimulationController {

    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserWorldRepository userWorldRepository;
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    @MessageMapping("/connect/")
    public void connect(@Payload String message) throws Exception{
        int worldID = Integer.parseInt(new Gson().fromJson(message, Map.class).get("worldID").toString());
        int userID = ((Double) new Gson().fromJson(message, Map.class).get("userID")).intValue();
        User user = userRepository.findById(userID).orElse(null);
        World world = worldRepository.findById(worldID).orElse(null);
        users.add(user);
        UserWorld userWorld = userWorldRepository.findByWorldAndUser(world.getId(), user.getId());
        if(userWorld == null){
            userWorld = new UserWorld();
            userWorld.setWorld(world);
            userWorld.setUser(user);
            userWorld = userWorldRepository.save(userWorld);
        }
        List<Creature> lstCreatures = creatureRepository.findAllByWorldAndUser(world.getId(), user.getId());
        if (creatures == null || creatures.size() == 0){
//            int cX = (int) Math.floor(Math.random() * 25);
//            int cY = (int) Math.floor(Math.random() * 25);
            int cX = 0;
            int cY = 0;
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
        User user = new User();
        user.setId(Integer.valueOf(message));
        users.remove(user);
        List<Creature> lstCreatures = creatureRepository.findAllByWorldAndUser(worldID, user.getId());
        creatures.removeAll(lstCreatures);
    }

    @MessageMapping("/chat/{worldID}")
    public void chat(@Payload String message, @PathParam("worldID") int worldID) throws Exception{
        sendingOperations.convertAndSend("/chat/" + worldID, message);
    }

    public void loop() throws InterruptedException {
        while(true){
            sendingOperations.convertAndSend("/creature", creatures);
            for (Creature c: creatures) {
                c.update();
                creatureRepository.save(c);
            }
            Thread.sleep(10000);
        }
    }
}
