package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "friend")
public class Friend {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private User user;

    public String getFullName(){
        return user.getFullName();
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", user=" + user.getFullName() +
                '}';
    }
}
