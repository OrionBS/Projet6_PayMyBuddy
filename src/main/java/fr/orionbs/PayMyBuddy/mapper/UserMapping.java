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

        List<String> friendsFullName = new ArrayList();
        List<String> friendsEmail = new ArrayList<>();

        if (user.getFriends() !=null) {
            for (Friend friend : user.getFriends()) {
                friendsFullName.add(friend.getFullName());
                friendsEmail.add(friend.getEmail());
            }
        }
        return new UserSession(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),user.getAmount(), friendsFullName,friendsEmail,user.getTransactions());
    };
}
