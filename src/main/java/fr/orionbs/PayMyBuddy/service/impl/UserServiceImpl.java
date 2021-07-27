package fr.orionbs.PayMyBuddy.service.impl;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.Friend;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        return userRepository.findByEmail(email) != null;
    }

    @Override
    public User updateUserDTO(UserDTO userDTO) {
        User oldUser = userRepository.findByEmail(userDTO.getEmail());

        oldUser.setFirstName(userDTO.getFirstName());
        oldUser.setLastName(userDTO.getLastName());

        return userRepository.save(oldUser);
    }

    @Override
    public User updateUserData(User user) {

        return userRepository.save(user);
    }


    @Override
    public Boolean addUser(UserDTO userDTO) {
        log.info("Service : Inscription de {}", userDTO);

        UserMapping userMapping = new UserMapping();

        User user = userMapping.userDtoToUserRepo(userDTO);

        if (userRepository.findByEmail(user.getEmail()) != null) {
            log.info("Utilisateur déjà présent {}", user.getEmail());
            return false;
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));

        log.info("Nouvel utilisateur {}", user);

        userRepository.save(user);

        return true;
    }

    @Override
    public Boolean addFriend(String emailUser, String emailFriend) {

        User user = userRepository.findByEmail(emailUser);
        User userFriend = userRepository.findByEmail(emailFriend);
        if (userFriend == null) {
            log.info("Utilisateur inconnu {} ", emailFriend);
            return false;
        }

        log.info("Ajout de {} à la liste de {}", emailFriend, emailUser);
        Friend friend = Friend.builder().id(userFriend.getId()).user(userFriend).build();
        user.getFriends().add(friend);
        userRepository.save(user);
        return true;
    }

    @Override
    public String deleteUsers() {
        log.info("Suppression des utilisateurs");
        userRepository.deleteAll();
        return "Utilisateurs supprimés.";
    }

    @Override
    public User findUser(String email) {
        log.info("Recherche d'un utilisateur {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean findUserWithEmailAndPassword(String email, String password) {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder().matches(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
