package fr.orionbs.PayMyBuddy.model;

import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Embeddable
public class UserTransaction {

    private String date;
    private String description;
    private float price;
    private Type type;
    private String sendTo;
    private String sendBy;

}
