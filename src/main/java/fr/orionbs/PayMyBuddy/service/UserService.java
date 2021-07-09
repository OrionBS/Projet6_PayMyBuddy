package fr.orionbs.PayMyBuddy.service;

import fr.orionbs.PayMyBuddy.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    Boolean addUser(User user);
    User addFriend(String emailUser, String emailFriend);
    String deleteUsers();
    User findUser(String email);
    Boolean findUserWithEmailAndPassword(String email, String password);
    Boolean isUserPresent(String email);
    User updateUser(User user);
}
