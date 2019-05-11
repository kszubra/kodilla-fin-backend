package com.kodilla.kodillafinalbackend.controler;

import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationDto;
import com.kodilla.kodillafinalbackend.mapper.ReservationMapper;
import com.kodilla.kodillafinalbackend.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @PostMapping("reservations")
    public void addReservation(@RequestBody ReservationCreationDto dto){
        Reservation reservation = reservationMapper.mapToReservationFromCreationDto(dto);
        reservationService.addReservation( reservation );
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

    //@PostMapping("reservations")
    //@Transactional
    //public ReservationDto updateReservation(@RequestBody ReservationDto updatingDto) {
    //    Reservation reservation = reservationService.getReservationById( updatingDto.getId() );


    //}
}
