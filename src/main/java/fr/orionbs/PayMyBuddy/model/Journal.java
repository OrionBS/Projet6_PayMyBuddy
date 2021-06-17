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
@Entity(name = "journal")
public class Journal {

    @Id
    @GeneratedValue
    private Integer id;
    @ElementCollection
    private List<JournalTransaction> journalTransactions;
    private float amount;
}
