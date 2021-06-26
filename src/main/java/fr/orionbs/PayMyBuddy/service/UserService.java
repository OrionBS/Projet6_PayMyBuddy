package fr.orionbs.PayMyBuddy.service;

import fr.orionbs.PayMyBuddy.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
    User addFriend(String emailUser, String emailFriend);
    String deleteUsers();
    User findUser(String email);
    Boolean isUserPresent(String email);
}
