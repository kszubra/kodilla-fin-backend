package com.kodilla.kodillafinalbackend.domain.dto;

import com.kodilla.kodillafinalbackend.enumeration.PaymentStatus;
import lombok.*;


import java.math.BigDecimal;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PaymentDto {

    private Long id;
    private PaymentStatus status;
    private BigDecimal value;
    private String paymentDate;

    public boolean hasValidDate() {
        Pattern datePattern = Pattern.compile("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        return datePattern.matcher( paymentDate ).matches();
    }
}
