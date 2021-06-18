package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String date;
    private String description;
    private String who;
    private Type type;
    private float amount;
}
