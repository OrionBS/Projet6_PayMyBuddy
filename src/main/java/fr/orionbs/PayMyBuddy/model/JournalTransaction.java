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
public class JournalTransaction {

    private String date;
    private String description;
    private float price;
}
