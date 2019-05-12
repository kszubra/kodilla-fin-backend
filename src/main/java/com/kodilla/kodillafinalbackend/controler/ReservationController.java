package com.kodilla.kodillafinalbackend.controler;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationDto;
import com.kodilla.kodillafinalbackend.mapper.ReservationMapper;
import com.kodilla.kodillafinalbackend.service.PaymentService;
import com.kodilla.kodillafinalbackend.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final PaymentService paymentService;

    @PostMapping("reservations")
    public void addReservation(@RequestBody ReservationCreationDto dto){
        reservationService.addReservation( reservationMapper.mapToReservationFromCreationDto(dto));
    }

    @GetMapping("reservations")
    public List<ReservationDto> getReservations() {
        return reservationMapper.mapToDtoList( reservationService.getAllReservations() );
    }

    @GetMapping("reservations/{id}")
    public ReservationDto getReservation(@PathVariable("id") Long id) {
        return reservationMapper.mapToDto( reservationService.getReservationById(id) );
    }

    @GetMapping("reservations/")
    public List<ReservationDto> getReservationBySurname(@RequestParam("surname") String surname) {
        return reservationMapper.mapToDtoList( reservationService.getReservationsBySurname(surname) );
    }

    @DeleteMapping("reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservationById(id);
    }

    @GetMapping("reservations/count/{city}")
    public Long countReservationsInCity(@PathVariable("city") String city) {
        return reservationService.getNumberOfReservationsInCity(city);
    }

    @PutMapping("reservations")
    @Transactional
    public ReservationDto updateReservation(@RequestBody ReservationDto updatingDto) {
        Reservation reservation = reservationService.getReservationById( updatingDto.getId() );
        Payment payment = paymentService.getPaymentById( updatingDto.getPaymentDto().getId() );

        /**
         * Checks differences on Reservation object
         */
        if(! reservation.getThereFlightDepartureCity().equals( updatingDto.getThereFlightDepartureCity() )) { reservation.setThereFlightDepartureCity( updatingDto.getThereFlightDepartureCity() ); }
        if(! reservation.getThereFlightDepartureAirportCode().equals( updatingDto.getThereFlightDepartureAirportCode() )) { reservation.setThereFlightDepartureAirportCode( updatingDto.getThereFlightDepartureAirportCode() ); }
        if(! reservation.getThereFlightDestinationCity().equals( updatingDto.getThereFlightDestinationCity() )) { reservation.setThereFlightDestinationCity( updatingDto.getThereFlightDestinationCity() ); }
        if(! reservation.getThereFlightDestinationAirportCode().equals( updatingDto.getThereFlightDestinationAirportCode() )) { reservation.setThereFlightDestinationAirportCode( updatingDto.getThereFlightDestinationAirportCode() ); }
        if(! reservation.getThereFlightDate().equals( updatingDto.getThereFlightDate() )) { reservation.setThereFlightDate(LocalDate.parse(updatingDto.getThereFlightDate())); }
        if(! reservation.getReturnFlightDepartureCity().equals( updatingDto.getReturnFlightDepartureCity() )) { reservation.setReturnFlightDepartureCity( updatingDto.getReturnFlightDepartureCity() ); }
        if(! reservation.getReturnFlightDepartureAirportCode().equals( updatingDto.getReturnFlightDepartureAirportCode() )) { reservation.setReturnFlightDepartureAirportCode( updatingDto.getReturnFlightDepartureAirportCode() ); }
        if(! reservation.getReturnFlightDestinationCity().equals( updatingDto.getReturnFlightDestinationCity() )) { reservation.setReturnFlightDestinationCity( updatingDto.getReturnFlightDestinationCity() ); }
        if(! reservation.getReturnFlightDestinationAirportCode().equals( updatingDto.getReturnFlightDestinationAirportCode() )) { reservation.setReturnFlightDestinationAirportCode( updatingDto.getReturnFlightDestinationAirportCode() ); }
        if(! reservation.getReturnFlightDate().equals( updatingDto.getReturnFlightDate() )) { reservation.setReturnFlightDate( LocalDate.parse(updatingDto.getReturnFlightDate()) ); }
        if(! reservation.getName().equals( updatingDto.getName() )) { reservation.setName( updatingDto.getName() ); }
        if(! reservation.getSurname().equals( updatingDto.getSurname() )) { reservation.setSurname( updatingDto.getSurname() ); }
        if(! reservation.getEmail().equals( updatingDto.getEmail() )) { reservation.setEmail( updatingDto.getEmail() ); }
        if(! reservation.getPrice().equals( updatingDto.getPrice() )) { reservation.setPrice( updatingDto.getPrice() ); }

        /**
         * Checks differences on Payment object
         */
        if(! payment.getStatus().equals(updatingDto.getPaymentDto().getStatus())) {payment.setStatus( updatingDto.getPaymentDto().getStatus() );}
        if(! payment.getValue().equals(updatingDto.getPaymentDto().getValue())) {payment.setValue( updatingDto.getPaymentDto().getValue() );}
        if(! payment.hasPaymentDate() && updatingDto.getPaymentDto().hasValidDate() ) { payment.setPaymentDate( LocalDate.parse(updatingDto.getPaymentDto().getPaymentDate()) ); }
        if(
                payment.hasPaymentDate()   &&
                        updatingDto.getPaymentDto().hasValidDate() &&
                        !( payment.getPaymentDate().toString().equals( updatingDto.getPaymentDto().getPaymentDate() )  )

        ) {payment.setPaymentDate( LocalDate.parse(updatingDto.getPaymentDto().getPaymentDate()) );}

        return reservationMapper.mapToDto(reservation);

    }
}
