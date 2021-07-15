package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    private TypeOfTransaction typeOfTransaction;
    private float amount;
    private String date;
    private String description;

    @OneToOne(cascade=CascadeType.ALL)
    private User collector;

    @OneToOne(cascade=CascadeType.ALL)
    private User sender;

    @Override
    public String toString() {

        var receiver = Arrays.asList(TypeOfTransaction.BankToUser,TypeOfTransaction.UserToUser).contains(typeOfTransaction) ? ", collector=" + collector.getFullName() : "";
        var emitter = Arrays.asList(TypeOfTransaction.UserToBank,TypeOfTransaction.UserToUser).contains(typeOfTransaction) ? ", sender=" + sender.getFullName() : "";
        return "Transaction{" +
                "id=" + id +
                ", typeOfTransaction=" + typeOfTransaction +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                emitter +
                receiver +
                "}";
    }
}
