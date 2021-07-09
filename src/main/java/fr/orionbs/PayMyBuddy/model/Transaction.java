package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    private TypeOfTransaction typeOfTransaction;
    private float amount;
    private String date;
    private String description;

    @OneToOne
    private User collector;

    @OneToOne
    private User sender;
}
