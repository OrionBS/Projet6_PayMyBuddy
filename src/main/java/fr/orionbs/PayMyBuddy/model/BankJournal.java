package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity(name = "bank_journal")
public class BankJournal {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name = "userIdFk")
    private Transaction transaction;
    private float amount;
}
