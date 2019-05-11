package com.kodilla.kodillafinalbackend.domain.dto;

import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PaymentDto {

    private Long id;
    private PaymentStatus status;
    private BigDecimal value;
    private LocalDate paymentDate;
}
