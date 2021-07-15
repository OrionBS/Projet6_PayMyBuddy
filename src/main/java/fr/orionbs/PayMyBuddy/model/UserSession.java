package fr.orionbs.PayMyBuddy.model;

import fr.orionbs.PayMyBuddy.model.Friend;
import fr.orionbs.PayMyBuddy.model.Transaction;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserSession {

    private Integer idSession;
    private String emailSession;
    private String firstNameSession;
    private String lastNameSession;
    private float amountSession;
    private List<Friend> friends;
    private List<Transaction> transactions;


}
