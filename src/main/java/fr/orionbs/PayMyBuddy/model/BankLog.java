package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity(name = "bank_logs")
public class BankLog {

    @Id
    @GeneratedValue
    private Integer id;

    private TypeOfTransaction typeOfTransaction;
    private float amount;
    private String date;
    private String description;
}
