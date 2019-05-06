package com.kodilla.kodillafinalbackend.domain;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class WeekendFlightOffer {
    private List<Flight> thereFlights;
    private List<Flight> returnFlights;
}
