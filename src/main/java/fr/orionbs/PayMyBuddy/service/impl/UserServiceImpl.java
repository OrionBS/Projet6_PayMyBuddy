package fr.orionbs.PayMyBuddy.service.impl;

import fr.orionbs.PayMyBuddy.model.Friend;
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
    public Boolean isUserPresent(String email) {
        if (findUser(email) == null) {
            return false;
        };
        return true;
    }

    @Override
    public User addUser(User user) {
        log.info("Service : Inscription de "+user);
        if (isUserPresent(user.getEmail())) {
            log.info("Utilisateur déjà présent. "+user);
            return user;
        }
        log.info("Nouvel utilisateur : "+user);
        return userRepository.save(user);
    }

    @Override
    public User addFriend(String emailUser, String emailFriend) {
        User user = userRepository.findByEmail(emailUser);
        if (!isUserPresent(emailFriend)) {
            log.error("Erreur: "+emailFriend+" n'existe pas.");
            return null;
        }

        log.info("Ajout de "+emailFriend+" à la liste de "+emailUser);
        user.getFriends().add(Friend.builder().user(userRepository.findByEmail(emailFriend)).build());
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
