package fr.orionbs.PayMyBuddy.dto;

import fr.orionbs.PayMyBuddy.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FriendDTO {

    private User user;

}
