package com.kodilla.kodillafinalbackend.mapper;

import com.kodilla.kodillafinalbackend.domain.Payment;
import com.kodilla.kodillafinalbackend.domain.dto.PaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PaymentMapper {

    public Payment mapToPayment(final PaymentDto dto) {
        return Payment.builder()
                .id( dto.getId() )
                .paymentDate( LocalDate.parse(dto.getPaymentDate()) )
                .status( dto.getStatus() )
                .value( dto.getValue() )
                .build();
    }

    public List<Payment> mapToPaymentList(final List<PaymentDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToPayment)
                .collect(Collectors.toList());
    }

    public PaymentDto mapToDto(final Payment payment) {
        return PaymentDto.builder()
                .id( payment.getId() )
                .paymentDate( payment.getPaymentDate().toString() )
                .status( payment.getStatus() )
                .value( payment.getValue() )
                .build();
    }

    public List<PaymentDto> mapToDtoList(final List<Payment> paymentList) {
        return paymentList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
