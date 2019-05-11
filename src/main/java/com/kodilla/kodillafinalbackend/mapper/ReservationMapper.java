package com.kodilla.kodillafinalbackend.mapper;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationMapper {
    private final PaymentMapper paymentMapper;

    public Reservation mapToReservationFromCreationDto(final ReservationCreationDto dto) {
        return Reservation.builder()
                .thereFlightDepartureCity( dto.getThereFlightDepartureCity() )
                .thereFlightDepartureAirportCode( dto.getThereFlightDepartureAirportCode() )
                .thereFlightDestinationCity( dto.getThereFlightDestinationCity() )
                .thereFlightDestinationAirportCode( dto.getThereFlightDestinationAirportCode() )
                .thereFlightDate( dto.getThereFlightDate() )
                .returnFlightDepartureCity( dto.getReturnFlightDepartureCity() )
                .returnFlightDepartureAirportCode( dto.getReturnFlightDepartureAirportCode() )
                .returnFlightDestinationCity( dto.getReturnFlightDestinationCity() )
                .returnFlightDestinationAirportCode( dto.getReturnFlightDestinationAirportCode() )
                .returnFlightDate( dto.getReturnFlightDate() )
                .name( dto.getName() )
                .surname( dto.getSurname() )
                .email( dto.getEmail() )
                .price( dto.getPrice() )
                .build();
    }

    public Reservation mapToReservation(final ReservationDto dto) {
        return Reservation.builder()
                .id( dto.getId() )
                .thereFlightDepartureCity( dto.getThereFlightDepartureCity() )
                .thereFlightDepartureAirportCode( dto.getThereFlightDepartureAirportCode() )
                .thereFlightDestinationCity( dto.getThereFlightDestinationCity() )
                .thereFlightDestinationAirportCode( dto.getThereFlightDestinationAirportCode() )
                .thereFlightDate( dto.getThereFlightDate() )
                .returnFlightDepartureCity( dto.getReturnFlightDepartureCity() )
                .returnFlightDepartureAirportCode( dto.getReturnFlightDepartureAirportCode() )
                .returnFlightDestinationCity( dto.getReturnFlightDestinationCity() )
                .returnFlightDestinationAirportCode( dto.getReturnFlightDestinationAirportCode() )
                .returnFlightDate( dto.getReturnFlightDate() )
                .name( dto.getName() )
                .surname( dto.getSurname() )
                .email( dto.getEmail() )
                .price( dto.getPrice() )
                .payment( paymentMapper.mapToPayment( dto.getPaymentDto() ) )
                .build();
    }

    public List<Reservation> mapToReservationList(final List<ReservationDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToReservation)
                .collect(Collectors.toList());
    }

    public ReservationDto mapToDto(final Reservation reservation) {
        return ReservationDto.builder()
                .id( reservation.getId() )
                .thereFlightDepartureCity( reservation.getThereFlightDepartureCity() )
                .thereFlightDepartureAirportCode( reservation.getThereFlightDepartureAirportCode() )
                .thereFlightDestinationCity( reservation.getThereFlightDestinationCity() )
                .thereFlightDestinationAirportCode( reservation.getThereFlightDestinationAirportCode() )
                .thereFlightDate( reservation.getThereFlightDate() )
                .returnFlightDepartureCity( reservation.getReturnFlightDepartureCity() )
                .returnFlightDepartureAirportCode( reservation.getReturnFlightDepartureAirportCode() )
                .returnFlightDestinationCity( reservation.getReturnFlightDestinationCity() )
                .returnFlightDestinationAirportCode( reservation.getReturnFlightDestinationAirportCode() )
                .returnFlightDate( reservation.getReturnFlightDate() )
                .name( reservation.getName() )
                .surname( reservation.getSurname() )
                .email( reservation.getEmail() )
                .price( reservation.getPrice() )
                .paymentDto( paymentMapper.mapToDto( reservation.getPayment() ) )
                .build();
    }

    public List<ReservationDto> mapToDtoList(final List<Reservation> reservationList) {
        return reservationList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
