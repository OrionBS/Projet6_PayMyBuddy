package fr.orionbs.PayMyBuddy.mapper;

import fr.orionbs.PayMyBuddy.dto.UserSession;
import fr.orionbs.PayMyBuddy.model.Friend;
import fr.orionbs.PayMyBuddy.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapping {

    public UserSession userRepoToUserSession(User user) {

        return new UserSession(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),user.getAmount(),user.getFriends() ,user.getTransactions());
    }
}
