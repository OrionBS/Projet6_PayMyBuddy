package fr.orionbs.PayMyBuddy.service;

import fr.orionbs.PayMyBuddy.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@Slf4j
public class NewUserPostConstruct {

    @Autowired
    UserService userService;

    /*@PostConstruct
    public void addingUser() {
        log.info("Post Construct : Nouvel Utilisateur.");
        User user = new User(null,"work@orionbs.fr","jackjack","Orion","Beauny",10000,new ArrayList<>(),new ArrayList<>());
        userService.addUser(user);
    };*/
}
