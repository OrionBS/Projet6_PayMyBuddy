package fr.orionbs.PayMyBuddy.mapper;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    public UserSession userRepoToUserSession(User user) {

        return new UserSession(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),user.getAmount(),user.getFriends() ,user.getTransactions());
    }

    public User userDtoToUserRepo(UserDTO userDTO) {

        return new User(null,userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstName(),userDTO.getLastName(),0,null,null);
    }
}
