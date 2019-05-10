package com.kodilla.kodillafinalbackend.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NotificationPreferenceCreationDto {
    private Long id;
    private Long userId;
    private String departureCity;
    private String destinationCity;
    private Integer minTemperature;
    private BigDecimal maxPrice;
}
