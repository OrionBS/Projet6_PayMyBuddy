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

    private String email;
    private String firstName;
    private String lastName;

    public String getFullName(){
        return firstName+" "+lastName;
    }
}
