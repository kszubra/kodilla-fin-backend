package com.kodilla.kodillafinalbackend.skyscanner;

import com.kodilla.kodillafinalbackend.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.skyscanner.domain.Flight;
import com.kodilla.kodillafinalbackend.skyscanner.mapper.CityAirportsResultMapper;
import com.kodilla.kodillafinalbackend.skyscanner.mapper.FlightConnectionsResultMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class SkyScannerFacade {
    private final CityAirportsResultMapper cityAirportsResultMapper;
    private final FlightConnectionsResultMapper flightConnectionsResultMapper;
    private final SkyScannerClient skyScannerClient;

    public List<Flight> getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date) {
        return flightConnectionsResultMapper
                .mapToFlightConnectionsResult( skyScannerClient.getFlightConnections(originAirportCode, destinationAirportCode, date) )
                .getConnections();
    }

    public List<Airport> getAirportsInCity(String city) {
        return cityAirportsResultMapper
                .mapToCityAirportsResult( skyScannerClient.getAirportsInCity(city) )
                .getAirports();
    }
}
