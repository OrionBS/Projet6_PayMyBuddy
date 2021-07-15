package fr.orionbs.PayMyBuddy.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
