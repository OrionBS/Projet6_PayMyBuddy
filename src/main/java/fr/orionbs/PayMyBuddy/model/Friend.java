package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "friend")
public class Friend {

    @Id
    @GeneratedValue
    private Integer id;

    private String friendEmail;

    @OneToOne
    private User user;

    public String getFullName(){
        return user.getFullName();
    }
}
