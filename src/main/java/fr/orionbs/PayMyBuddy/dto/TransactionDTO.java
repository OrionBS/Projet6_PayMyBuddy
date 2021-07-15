package fr.orionbs.PayMyBuddy.dto;

import fr.orionbs.PayMyBuddy.model.TypeOfTransaction;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class TransactionDTO {

    private TypeOfTransaction typeOfTransaction;
    private String collectorEmail;
    private String description;
    private float amount;

}
