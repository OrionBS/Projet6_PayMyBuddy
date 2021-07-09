package fr.orionbs.PayMyBuddy.dto;

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
    private List<String> friendsFullNameSession;
    private List<String> friendsEmailSession;
    private List<Transaction> transactions;


}
