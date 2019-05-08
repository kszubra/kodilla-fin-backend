package com.kodilla.kodillafinalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FlightSearchRequest {
    private String departureCity;
    private String departureAirport;
    private String destinationCity;
    private String destinationAirport;
    private LocalDate departureDay;

    @Override
    public String toString() {
        return "FlightSearchRequest{" +
                "From:" + departureCity + " " +
                "(" + departureAirport + "), " +
                "To: " + destinationCity + " " +
                "(" + destinationAirport + "), " +
                "on: " + departureDay +
                '}';
    }
}
