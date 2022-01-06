package br.com.tqi.evolution.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long code;
    private Double value;
    private ZonedDateTime firstInstallmentDate;
    private int numberOfInstallments;
    private int numberOfInstallmentsPayed;
    private Double remainingValue;
}
