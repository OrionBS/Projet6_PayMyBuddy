package fr.orionbs.PayMyBuddy.service;

import fr.orionbs.PayMyBuddy.model.User;

public interface UserService {
    User addUser(User user);
    User addFriend(String emailUser, String emailFriend);
}
