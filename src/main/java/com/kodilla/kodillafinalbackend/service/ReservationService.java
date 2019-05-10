package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import com.kodilla.kodillafinalbackend.exceptions.ReservationNotFoundException;
import com.kodilla.kodillafinalbackend.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    @Transactional
    public Reservation addReservation(final Reservation reservation) {
        Payment payment = Payment.builder()
                .value( reservation.getPrice() )
                .status(PaymentStatus.AWAITING)
                .build();
        paymentService.addPayment(payment);
        reservation.setPayment(payment);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(final Long id) {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public List<Reservation> getReservationsByEmail(final String email) {
        return reservationRepository.findAllByEmail(email);
    }

    public List<Reservation> getReservationsByDestinationCity(final String city) {
        return reservationRepository.findAllByThereFlightDestinationCity(city);
    }

    public List<Reservation> getReservationsBySurname(final String surname) {
        return reservationRepository.findAllBySurname(surname);
    }

    public Long getNumberOfReservationsInCity(final String city) {
        return reservationRepository.countAllByThereFlightDestinationCity(city);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }

    @Transactional
    public void deleteReservationById(final Long id) {
        reservationRepository.deleteById(id);
    }

}
