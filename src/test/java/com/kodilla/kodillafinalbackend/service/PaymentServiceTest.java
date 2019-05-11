package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import com.kodilla.kodillafinalbackend.exceptions.PaymentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

    @Before
    public void cleanUp() {
        paymentService.deleteAllPayments();
    }

    @Test
    public void testAddPayment() {
        //Given
        Payment testPayment = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.PAID)
                .value(BigDecimal.valueOf(950.59))
                .build();

        //When
        paymentService.addPayment(testPayment);
        Long id = testPayment.getId();
        Payment result = paymentService.getPaymentById(id);

        //Then
        assertEquals(result, testPayment);
    }

    @Test(expected = PaymentNotFoundException.class)
    public void testGetPaymentByIdWithWrongId() {
        //Given, When & Then
        Payment payment = paymentService.getPaymentById(21L);
    }


    @Test
    public void testGetAllPayments() {
        //Given
        Payment testPaymentOne = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.PAID)
                .value(BigDecimal.valueOf(950.59))
                .build();
        Payment testPaymentTwo = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(9.59))
                .build();
        Payment testPaymentThree = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(11.32))
                .build();
        List<Payment> payments = Arrays.asList(testPaymentOne, testPaymentTwo, testPaymentThree);
        payments.forEach(e -> paymentService.addPayment(e));

        //When
        List<Payment> result = paymentService.getAllPayments();

        //Then
        assertEquals(result, payments);
    }

    @Test
    public void testGetPaymentsByDate() {
        //Given
        Payment testPaymentOne = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.PAID)
                .value(BigDecimal.valueOf(950.59))
                .build();
        Payment testPaymentTwo = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(9.59))
                .build();
        Payment testPaymentThree = Payment.builder()
                .paymentDate(LocalDate.of(1657, 12, 11))
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(11.32))
                .build();
        List<Payment> payments = Arrays.asList(testPaymentOne, testPaymentTwo, testPaymentThree);
        payments.forEach(e -> paymentService.addPayment(e));

        //When
        List<Payment> result = paymentService.getPaymentsByDate(LocalDate.of(1657, 12, 11));

        //Then
        assertEquals(1, result.size());
        assertTrue(result.contains(testPaymentThree));
    }

    @Test
    public void testDeleteAllPayments() {
        //Given
        Payment testPaymentOne = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.PAID)
                .value(BigDecimal.valueOf(950.59))
                .build();
        Payment testPaymentTwo = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(9.59))
                .build();
        Payment testPaymentThree = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(11.32))
                .build();
        List<Payment> payments = Arrays.asList(testPaymentOne, testPaymentTwo, testPaymentThree);
        payments.forEach(e -> paymentService.addPayment(e));

        //When
        paymentService.deleteAllPayments();
        List<Payment> result = paymentService.getAllPayments();

        //Then
        assertEquals(0, result.size());

    }

    @Test
    public void testDeletePaymentById() {
        //Given
        Payment testPaymentOne = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.PAID)
                .value(BigDecimal.valueOf(950.59))
                .build();
        Payment testPaymentTwo = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(9.59))
                .build();
        Payment testPaymentThree = Payment.builder()
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.AWAITING)
                .value(BigDecimal.valueOf(11.32))
                .build();
        List<Payment> payments = Arrays.asList(testPaymentOne, testPaymentTwo, testPaymentThree);
        payments.forEach(e -> paymentService.addPayment(e));

        //When
        paymentService.deletePaymentById( testPaymentTwo.getId() );
        List<Payment> result = paymentService.getAllPayments();

        //Then
        assertEquals(2, result.size());
        assertFalse( result.contains(testPaymentTwo) );
    }
}