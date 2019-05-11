package com.kodilla.kodillafinalbackend.controler;

import com.google.gson.Gson;
import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.dto.PaymentDto;
import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import com.kodilla.kodillafinalbackend.mapper.PaymentMapper;
import com.kodilla.kodillafinalbackend.service.PaymentService;
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
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private PaymentMapper paymentMapper;

    private Gson gson = new Gson();

    @Test
    public void testAddPayment() throws Exception {
        //Given
        PaymentDto dto = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        when( paymentService.addPayment(payment) ).thenReturn(payment);
        when( paymentMapper.mapToPayment(dto) ).thenReturn(payment);
        when( paymentService.getPaymentById(1L) ).thenReturn(payment);

        String jsonContent = gson.toJson(dto);

        // When & Then
        mockMvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPayment() throws Exception {
        //Given
        PaymentDto dto = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        when( paymentMapper.mapToDto(payment) ).thenReturn(dto);
        when( paymentService.getPaymentById(1L) ).thenReturn(payment);

        // When & Then
        mockMvc.perform(get("/payments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("AWAITING")))
                .andExpect(jsonPath("$.value", is(250.55)));
        verify(paymentService, times(1)).getPaymentById(1L);
    }

    @Test
    public void getAllPayments() throws Exception {
        //Given
        PaymentDto dtoOne = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment paymentOne = Payment.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        PaymentDto dtoTwo = PaymentDto.builder()
                .id(2L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment paymentTwo = Payment.builder()
                .id(2L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        List<Payment> payments = Arrays.asList(paymentOne, paymentTwo);
        List<PaymentDto> dtos = Arrays.asList(dtoOne, dtoTwo);

        when( paymentMapper.mapToDtoList(payments) ).thenReturn(dtos);
        when( paymentService.getAllPayments() ).thenReturn(payments);

        // When & Then
        mockMvc.perform(get("/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("AWAITING")))
                .andExpect(jsonPath("$[0].value", is(250.55)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is("REJECTED")))
                .andExpect(jsonPath("$[1].value", is(250.55)));
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    public void updatePayment() throws Exception {
        //Given
        PaymentDto dto = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(100.55) )
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        PaymentDto updateDto = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(100.55) )
                .build();

        when( paymentService.getPaymentById(1L) ).thenReturn(payment);
        when( paymentMapper.mapToDto(payment) ).thenReturn(dto);


        String jsonContent = gson.toJson(updateDto);

        //When & then
        mockMvc.perform(put("/payments").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is(100.55)))
                .andExpect(jsonPath("$.status", is("REJECTED")));

        verify(paymentService, times(1)).getPaymentById(1L);


    }

    @Test
    public void getPaymentsByDate() throws Exception {
        //Given
        PaymentDto dtoOne = PaymentDto.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment paymentOne = Payment.builder()
                .id(1L)
                .status(PaymentStatus.AWAITING)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        PaymentDto dtoTwo = PaymentDto.builder()
                .id(2L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        Payment paymentTwo = Payment.builder()
                .id(2L)
                .status(PaymentStatus.REJECTED)
                .value( BigDecimal.valueOf(250.55) )
                .build();

        List<Payment> payments = Arrays.asList(paymentOne, paymentTwo);
        List<PaymentDto> dtos = Arrays.asList(dtoOne, dtoTwo);

        when( paymentMapper.mapToDtoList(payments) ).thenReturn(dtos);
        when( paymentService.getPaymentsByDate(LocalDate.parse("2019-05-15")) ).thenReturn(payments);

        // When & Then
        mockMvc.perform(get("/payments/?date=2019-05-15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("AWAITING")))
                .andExpect(jsonPath("$[0].value", is(250.55)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is("REJECTED")))
                .andExpect(jsonPath("$[1].value", is(250.55)));
        verify(paymentService, times(1)).getPaymentsByDate(LocalDate.parse("2019-05-15")) ;
    }

}