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
@Entity(name = "user")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email","password","firstName","lastName"})
})
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private float amount;
    @ElementCollection
    private List<String> friends;
    @ElementCollection
    private List<UserTransaction> userTransactions;
    @ElementCollection
    private List<JournalTransaction> journalTransactions;
}
