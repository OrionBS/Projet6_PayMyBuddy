package fr.orionbs.PayMyBuddy.service;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    Boolean addUser(UserDTO userDTO);
    Boolean addFriend(String emailUser, String emailFriend);
    String deleteUsers();
    User findUser(String email);
    Boolean findUserWithEmailAndPassword(String email, String password);
    Boolean isUserPresent(String email);
    User updateUserDTO(UserDTO userDTO);
    User updateUserData(User user);
}
