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
        @UniqueConstraint(columnNames = {"email","password"})
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
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "userIdFk")
    private List<Friend> friends;

    @OneToMany
    @JoinColumn(name = "userIdFk")
    private List<Transaction> transactions;

    public String getFullName(){
        return firstName+" "+lastName;
    }
}
