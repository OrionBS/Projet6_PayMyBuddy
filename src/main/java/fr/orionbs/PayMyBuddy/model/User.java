package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
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
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "userIdFk")
    private List<Transaction> transactions = new ArrayList<>();

    public String getFullName(){
        return firstName+" "+lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", amount=" + amount +
                ", friends=" + friends +
                ", transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
