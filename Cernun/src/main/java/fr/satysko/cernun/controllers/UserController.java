package fr.satysko.cernun.controllers;

import fr.satysko.cernun.exceptions.UserException;
import fr.satysko.cernun.models.RestResponse;
import fr.satysko.cernun.models.User;
import fr.satysko.cernun.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {


    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //Liste tous les utilisateurs
    @GetMapping({"", "/", "/all"})
    public RestResponse<List<User>> getAllUsers(){
        logger.info("Recherche de tous les utilisateurs");
        logger.warn("Recherche de tous les utilisateurs");
        logger.error("Recherche de tous les utilisateurs");
        RestResponse<List<User>> response;
        try{
            List<User> users = userService.findAll();
            if(users == null || users.size() == 0){
                response = new RestResponse<>(new UserException("Aucun utilisateur trouvés"), 204);
            }else {
                response = new RestResponse<>(users);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Liste tous les utilisateurs d'un monde défini
    @GetMapping({"/all/{id}"})
    public RestResponse<List<User>> getAllUsersWorld(@PathVariable("id") int id){
        RestResponse<List<User>> response;
        try{
            List<User> users = userService.findAll(id);
            if(users == null || users.size() == 0){
                response = new RestResponse<>(new UserException("Aucun utilisateur trouvé pour le monde n°" + id), 204);
            }else {
                response = new RestResponse<>(users);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Retourne un utilisateur par son id
    @GetMapping("/find/{id}")
    public RestResponse<User> findById(@PathVariable("id") int id){
        RestResponse<User> response;
        try {
            User user = userService.find(id);
            if(user == null){
                response = new RestResponse<User>(new UserException("L'utilisateur n°" + id + " n'existe pas"), 204);
            }else {
                response = new RestResponse<>(user);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Retourne un utilisateur par son Name et Password
    @PostMapping("/connect")
    public RestResponse<User> connect(@RequestBody User u){
        RestResponse<User> response;
        try {
            User user = userService.connect(u);
            if(user == null){
                response = new RestResponse<User>(new UserException("Association UserName - mot de passe incorrect"), 204);
            }else {
                response = new RestResponse<>(user);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Vérifie la présence d'un utilisateur possedant un username ou AccountName
    @GetMapping("/verify/{name}")
    public RestResponse<String> verifyName(@PathVariable("name") String name){
        RestResponse<String> response = null;
        try {
            if(userService.verify(name)){
                response = new RestResponse<>("Ce nom est disponible");
            }else {
                response = new RestResponse<String>(new UserException("Ce nom est déjà utilisé"), 204);
            }
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }

    //Crée un utilisateur
    @PostMapping("/create")
    public RestResponse<User> create(@RequestBody User u){
        RestResponse<User> response = null;
        try {
            if(u.getAccountName().equals(u.getUserName())){
                response = new RestResponse<User>(new UserException("Les champs nom d'utilisateur et nom de compte doivent être différents"), 204);
            }else {
                User user = userService.create(u);
                if (user == null) {
                    response = new RestResponse<User>(new UserException("L'utilisateur n'a pas pu être créé"), 204);
                } else {
                    response = new RestResponse<>(user);
                }
            }
        }catch (DataIntegrityViolationException e){
            response = new RestResponse<>(new UserException("Nom d'utilisateur ou nom de compte déjà existant"), 204);
        }catch (Exception e){
            response = new RestResponse<>(e, 400);
        }
        return response;
    }


    //Met à jour un utilisateur
    @PutMapping({"", "/"})
    public RestResponse<User> update(@RequestBody User user){
        RestResponse<User> response = null;

        return response;
    }

    //Supprime un utilisateur
    @DeleteMapping("/{id}")
    public RestResponse<Boolean> delete(@PathVariable("id") int id){
        RestResponse<Boolean> response = null;

        return response;
    }

    //Ajoute l'image
//    @PostMapping(value="/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Picture uploadFile(@PathVariable("id") int id, @RequestParam(name="monument_file") MultipartFile file) throws IOException {
//        return userService.uploadFile(id, file);
//    }

}
