package com.kodilla.kodillafinalbackend.controler;

import com.google.gson.Gson;
import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.Reservation;
import com.kodilla.kodillafinalbackend.domain.dto.PaymentDto;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.ReservationDto;
import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import com.kodilla.kodillafinalbackend.mapper.PaymentMapper;
import com.kodilla.kodillafinalbackend.mapper.ReservationMapper;
import com.kodilla.kodillafinalbackend.repository.StartupArgsRepository;
import com.kodilla.kodillafinalbackend.service.PaymentService;
import com.kodilla.kodillafinalbackend.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StartupArgsRepository repository;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private PaymentMapper paymentMapper;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private ReservationMapper reservationMapper;

    private Gson gson = new Gson();

    @Test
    public void testAddReservation() throws Exception {
        //Given
        ReservationCreationDto creationDto = ReservationCreationDto.builder()
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2019,10,16))
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2019,10,18) )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        when(reservationService.addReservation(reservation)).thenReturn(reservation);
        when(reservationMapper.mapToReservationFromCreationDto(creationDto)).thenReturn(reservation);

        String jsonContent = gson.toJson(creationDto);

        // When & Then
        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReservations() throws Exception {
        //Given
        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2019,10,16))
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2019,10,18) )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        ReservationDto reservationDtoTwo = ReservationDto.builder()
                .id(2L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "Bob" )
                .surname( "Marley" )
                .email( "mail@mail" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservationTwo = Reservation.builder()
                .id(2L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2019,10,16))
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2019,10,18) )
                .name( "Bob" )
                .surname( "Marley" )
                .email( "mail@mail" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        List<Reservation> reservations = Arrays.asList(reservation, reservationTwo);
        List<ReservationDto> dtos = Arrays.asList(reservationDto, reservationDtoTwo);

        when(reservationService.getAllReservations()).thenReturn(reservations);
        when(reservationMapper.mapToDtoList(reservations)).thenReturn(dtos);

        //When & then
        mockMvc.perform(get("/reservations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservations", hasSize(2)))
                .andExpect(jsonPath("$.reservations[0].name", is("John")))
                .andExpect(jsonPath("$.reservations[0].surname", is("Rambo")))
                .andExpect(jsonPath("$.reservations[1].name", is("Bob")))
                .andExpect(jsonPath("$.reservations[1].surname", is("Marley")));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    public void testGetReservation() throws Exception {
        //Given
        ReservationDto reservationDto = ReservationDto.builder()
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2019,10,16))
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2019,10,18) )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(reservationMapper.mapToDto(reservation)).thenReturn(reservationDto);

        //When & then
        mockMvc.perform(get("/reservations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.surname", is("Rambo")));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    public void testGetReservationBySurname() throws Exception {
        //Given
        ReservationDto reservationDto = ReservationDto.builder()
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .build();
        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .thereFlightDate(LocalDate.of(2019,10,16))
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .returnFlightDate( LocalDate.of(2019,10,18) )
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(new Payment())
                .build();

        List<Reservation> reservations = Arrays.asList(reservation);
        List<ReservationDto> dtos = Arrays.asList(reservationDto);

        when(reservationService.getReservationsBySurname("Rambo")).thenReturn(reservations);
        when(reservationMapper.mapToDtoList(reservations)).thenReturn(dtos);

        //When & then
        mockMvc.perform(get("/reservations/?surname=Rambo").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservations", hasSize(1)))
                .andExpect(jsonPath("$.reservations[0].name", is("John")))
                .andExpect(jsonPath("$.reservations[0].surname", is("Rambo")));
        verify(reservationService, times(1)).getReservationsBySurname("Rambo");
    }

    @Test
    public void testDeleteReservation() throws Exception {
        //When & Then
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());
        verify(reservationService, times(1)).deleteReservationById(1L);
    }

    @Test
    public void testCountReservationsInCity() throws Exception {
        //When & then
        when(reservationService.getNumberOfReservationsInCity("Hanover")).thenReturn(1L);

        mockMvc.perform(get("/reservations/count/Hanover"))
                .andExpect(jsonPath("$", is(1)))
                .andExpect(status().isOk());
        verify(reservationService, times(1)).getNumberOfReservationsInCity("Hanover");
    }

    @Test
    public void testUpdateReservation() throws Exception {
        //Given
        PaymentDto paymentDto = PaymentDto.builder()
                .id(2L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .paymentDate("2019-07-07")
                .build();

        Payment payment = Payment.builder()
                .id(2L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .paymentDate(LocalDate.parse("2019-07-07"))
                .build();

        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WMI" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .thereFlightDate("2019-07-07")
                .returnFlightDate("2019-07-07")
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .paymentDto(paymentDto)
                .price(BigDecimal.valueOf(555,66))
                .build();

        ReservationDto updateDto = ReservationDto.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WMI" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .thereFlightDate("2019-07-07")
                .returnFlightDate("2019-07-07")
                .name( "Johnson" )
                .surname( "Johnson" )
                .email( "rambo@rambo.com" )
                .paymentDto(paymentDto)
                .price(BigDecimal.valueOf(555,66))
                .build();

        Reservation reservation = Reservation.builder()
                .id(1L)
                .thereFlightDepartureCity( "Warsaw" )
                .thereFlightDepartureAirportCode( "WAW" )
                .thereFlightDestinationCity( "Hanover" )
                .thereFlightDestinationAirportCode( "HAJ" )
                .returnFlightDepartureCity( "Hanover" )
                .returnFlightDepartureAirportCode( "HAJ" )
                .returnFlightDestinationCity( "Warsaw" )
                .returnFlightDestinationAirportCode( "WAW" )
                .thereFlightDate(LocalDate.parse("2019-07-07"))
                .returnFlightDate(LocalDate.parse("2019-07-07"))
                .name( "John" )
                .surname( "Rambo" )
                .email( "rambo@rambo.com" )
                .price(BigDecimal.valueOf(555,66))
                .payment(payment)
                .build();

        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(reservationMapper.mapToDto(reservation)).thenReturn(reservationDto);
        when(paymentService.getPaymentById(2L)).thenReturn(payment);
        when(paymentMapper.mapToPayment(paymentDto)).thenReturn(payment);
        when(paymentMapper.mapToDto(payment)).thenReturn(paymentDto);
        when(paymentMapper.mapToDto(payment)).thenReturn(paymentDto);

        String jsonContent = gson.toJson(updateDto);

        // When & Then
        mockMvc.perform(put("/reservations").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.thereFlightDepartureAirportCode", is("WMI")));
        verify(reservationService, times(1)).getReservationById(1L);
    }
}