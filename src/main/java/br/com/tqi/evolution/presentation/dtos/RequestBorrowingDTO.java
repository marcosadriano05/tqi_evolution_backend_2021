package br.com.tqi.evolution.presentation.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class RequestBorrowingDTO {
    private Double value;
    private Date firstInstallmentDate;
    private int numberOfInstallments;
}
