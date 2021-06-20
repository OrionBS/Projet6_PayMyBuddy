package fr.orionbs.PayMyBuddy.service.impl;

import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        log.info("Inscription de "+user);
        User userAlreadyIn = userRepository.findByEmail(user.getEmail());
        if(userAlreadyIn == null) {
            log.info("Nouvel utilisateur : "+user);
            return userRepository.save(user);
        }
        log.info("Utilisateur déjà présent. "+userAlreadyIn);
        return userAlreadyIn;
    }

    @Override
    public User addFriend(String emailUser, String emailFriend) {
        User user = userRepository.findByEmail(emailUser);
        if (userRepository.findByEmail(emailFriend) == null) {
            log.error("Erreur: "+emailFriend+" n'existe pas.");
            return null;
        }

        log.info("Ajout de "+emailFriend+" à la liste de "+emailUser);
        List<String> listOfFriends = user.getFriends();
        listOfFriends.add(emailFriend);
        user.setFriends(listOfFriends);

        return userRepository.save(user);
    }

    @Override
    public String deleteUsers() {
        log.info("Suppression des utilisateurs.");
        userRepository.deleteAll();
        return "Utilisateurs supprimés.";
    }

    @Override
    public User findUser(String email) {
        log.info("Recherche d'un utilisateur :");
        return userRepository.findByEmail(email);
    }
}
