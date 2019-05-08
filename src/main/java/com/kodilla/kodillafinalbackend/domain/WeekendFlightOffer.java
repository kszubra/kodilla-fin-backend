package com.kodilla.kodillafinalbackend.domain;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeekendFlightOffer {
    private String homeCity;
    private String destinationCity;
    private BigDecimal expectedTemperature;
    private List<Flight> thereFlights;
    private List<Flight> returnFlights;
}
