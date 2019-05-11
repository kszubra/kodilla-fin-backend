package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PaymentService paymentService;

    @Before
    public void cleanUp(){
        reservationService.deleteAllReservations();
    }

    @Test
    public void testAddReservation() {
        //Given
        Reservation testReservation = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();

        reservationService.addReservation(testReservation);

        //When
        Reservation result = reservationService.getReservationById( testReservation.getId() );

        //Then
        assertNotNull( result.getPayment() );
        assertEquals(testReservation, result);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void testGettingReservationByNotExistingId() {
        //Given, when & then
        Reservation result = reservationService.getReservationById(34L);
    }


    @Test
    public void testGetReservationsBySurname() {
        //Given
        Reservation testReservationOne = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        Reservation testReservationThree = Reservation.builder()
                .name("John")
                .surname("Rambo2")
                .email("rambo2@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("London")
                .thereFlightDestinationAirportCode("LHR")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();

        reservationService.addReservation(testReservationOne);
        reservationService.addReservation(testReservationTwo);
        reservationService.addReservation(testReservationThree);

        //When
        List<Reservation> result = reservationService.getReservationsBySurname("Rambo");

        //Then
        assertEquals(2, result.size());
        assertTrue(result.contains(testReservationOne));
        assertTrue(result.contains(testReservationTwo));
    }

    @Test
    public void testGetNumberOfReservationsInCity() {
        //Given
        Reservation testReservationOne = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        Reservation testReservationThree = Reservation.builder()
                .name("John")
                .surname("Rambo2")
                .email("rambo2@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("London")
                .thereFlightDestinationAirportCode("LHR")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();

        reservationService.addReservation(testReservationOne);
        reservationService.addReservation(testReservationTwo);
        reservationService.addReservation(testReservationThree);

        //When
        long result = reservationService.getNumberOfReservationsInCity("Hanover");

        //Then
        assertEquals(2L, result);
    }

    @Test
    public void testGetAllReservations() {
        //Given
        Reservation testReservationOne = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        Reservation testReservationThree = Reservation.builder()
                .name("John")
                .surname("Rambo2")
                .email("rambo2@rambo.com")
                .price(BigDecimal.valueOf(15.55))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("London")
                .thereFlightDestinationAirportCode("LHR")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();

        reservationService.addReservation(testReservationOne);
        reservationService.addReservation(testReservationTwo);
        reservationService.addReservation(testReservationThree);

        //When
        List<Reservation> result = reservationService.getAllReservations();

        //Then
        assertEquals(3, result.size());
        assertEquals(BigDecimal.valueOf(593.21), result.get(0).getPayment().getValue() );
        assertEquals(BigDecimal.valueOf(250.99), result.get(1).getPayment().getValue() );
        assertEquals(BigDecimal.valueOf(15.55), result.get(2).getPayment().getValue() );
    }

    @Test
    public void testDeleteAllReservations() {
        //Given
        Reservation testReservation = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        reservationService.addReservation(testReservation);
        reservationService.addReservation(testReservationTwo);

        //When
        reservationService.deleteAllReservations();
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Payment> payments = paymentService.getAllPayments();

        //Then
        assertEquals(0, reservations.size() );
        assertEquals(0, payments.size());
    }

    @Test
    public void testDeleteReservationById() {
        //Given
        Reservation testReservation = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(593.21))
                .thereFlightDepartureCity("Warsaw")
                .thereFlightDepartureAirportCode("WMI")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2019,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Warsaw")
                .returnFlightDestinationAirportCode("WMI")
                .returnFlightDate(LocalDate.of(2019,5,12))
                .build();
        Reservation testReservationTwo = Reservation.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .price(BigDecimal.valueOf(250.99))
                .thereFlightDepartureCity("Gdańsk")
                .thereFlightDepartureAirportCode("GDA")
                .thereFlightDestinationCity("Hanover")
                .thereFlightDestinationAirportCode("HAJ")
                .thereFlightDate(LocalDate.of(2020,5,10))
                .returnFlightDepartureCity("Hanover")
                .returnFlightDepartureAirportCode("HAJ")
                .returnFlightDestinationCity("Gdańsk")
                .returnFlightDestinationAirportCode("GDA")
                .returnFlightDate(LocalDate.of(2020,5,12))
                .build();
        reservationService.addReservation(testReservation);
        reservationService.addReservation(testReservationTwo);

        //When
        Long reservationToDeleteId = testReservation.getId();
        Payment paymentToDelete = testReservation.getPayment();

        reservationService.deleteReservationById(reservationToDeleteId);
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Payment> payments = paymentService.getAllPayments();

        //Then
        assertEquals(1, reservations.size() );
        assertEquals(1, payments.size());
        assertFalse(payments.contains(paymentToDelete));
    }
}