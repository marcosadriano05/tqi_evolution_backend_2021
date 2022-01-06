package br.com.tqi.evolution.usecases;

import br.com.tqi.evolution.domain.Borrow;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RequestBorrowingTest {
    @Test
    public void shouldThrowIfNumberOfInstallmentsIsGreaterThan60 () {
        RequestBorrowing requestBorrowing = new RequestBorrowing();
        try {
            requestBorrowing.execute(100.0, ZonedDateTime.now(), 61);
            fail();
        } catch (Exception exception) {
            assertEquals("Number of installments must be less or equals to 60", exception.getMessage());
        }
    }

    @Test
    public void shouldThrowIfNumberOfInstallmentsIsLessThan1 () {
        RequestBorrowing requestBorrowing = new RequestBorrowing();
        try {
            requestBorrowing.execute(100.0, ZonedDateTime.now(), 0);
            fail();
        } catch (Exception exception) {
            assertEquals("Number of installments must be greater or equals to 1", exception.getMessage());
        }
    }

    @Test
    public void shouldThrowIfFirstInstallmentDateIsGreaterThan3MonthsFromActualDate () {
        RequestBorrowing requestBorrowing = new RequestBorrowing();
        try {
            requestBorrowing.execute(100.0, ZonedDateTime.now().plusMonths(3).plusSeconds(1), 60);
            fail();
        } catch (Exception exception) {
            assertEquals("The first installment date must be less or equals to 3 months from today", exception.getMessage());
        }
    }

    @Test
    public void shouldTReturnABorrowIfSuccess () throws Exception {
        RequestBorrowing requestBorrowing = new RequestBorrowing();
        Borrow borrow = requestBorrowing.execute(100.0, ZonedDateTime.now(), 60);
        assertEquals(100.0, borrow.getValue());
    }
}