package com.kodilla.kodillafinalbackend.external.api.skyscanner.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Flight {
    private String departureCity;
    private String destinationCity;
    private String departureAirport;
    private String destinationAirport;
    private BigDecimal minPrice;
    private boolean direct;
    private List<String> carriers;
    private LocalDate departureDate;

    @Override
    public String toString() {
        return "Flight{" +
                "departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", destinationAirport='" + destinationAirport + '\'' +
                ", minPrice=" + minPrice +
                ", direct=" + direct +
                ", carriers=" + carriers +
                ", departureDate=" + departureDate +
                '}';
    }
}
