package com.kodilla.kodillafinalbackend.skyscanner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class SkyScannerFacade {
    private final CityAirportsResultMapper cityAirportsResultMapper;
    private final FlightConnectionsResultMapper flightConnectionsResultMapper;
    private final SkyScannerClient skyScannerClient;

    public FlightConnectionsResult getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date) {
        return flightConnectionsResultMapper.mapToFlightConnectionsResult( skyScannerClient.getFlightConnections(originAirportCode, destinationAirportCode, date) );
    }

    public CityAirportsResult getAirportsInCity(String city) {
        return cityAirportsResultMapper.mapToCityAirportsResult( skyScannerClient.getAirportsInCity(city) );
    }
}
