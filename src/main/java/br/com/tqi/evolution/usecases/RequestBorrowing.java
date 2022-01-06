package br.com.tqi.evolution.usecases;

import br.com.tqi.evolution.domain.Borrow;

import java.time.ZonedDateTime;

public class RequestBorrowing {

    public Borrow execute (Double value, ZonedDateTime firstInstallmentDate, int numberOfInstallments) throws Exception {
        if (numberOfInstallments > 60) {
            throw new Exception("Number of installments must be less or equals to 60");
        }
        if (numberOfInstallments < 1) {
            throw new Exception("Number of installments must be greater or equals to 1");
        }
        ZonedDateTime afterThreeMonths = ZonedDateTime.now().plusMonths(3);
        if (firstInstallmentDate.isAfter(afterThreeMonths)) {
            throw new Exception("The first installment date must be less or equals to 3 months from today");
        }
        Borrow borrow = new Borrow();
        borrow.setValue(value);
        borrow.setFirstInstallmentDate(firstInstallmentDate);
        borrow.setNumberOfInstallments(numberOfInstallments);
        borrow.setRemainingValue(value);
        return borrow;
    }
}
