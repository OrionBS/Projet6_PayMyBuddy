package fr.orionbs.PayMyBuddy.api;

import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public List<User> getUsers() {
        log.info("API : Récupération des utilisateurs.");
        return userService.getUsers();
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(path = "/users")
    public String deleteUsers() {
        return userService.deleteUsers();
    }
}
