package com.kodilla.kodillafinalbackend.external.api.skyscanner;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.mapper.CityAirportsResultMapper;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.mapper.FlightConnectionsResultMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SkyScannerFacade {
    private final CityAirportsResultMapper cityAirportsResultMapper;
    private final FlightConnectionsResultMapper flightConnectionsResultMapper;
    private final SkyScannerClient skyScannerClient;

    /***
     * API may respond with flights having different departure date than provided in request. Before passing the response
     * method filteres out records with invalid departure date and not direct flights. This operation cannot be performed
     * in mapper because response from the API does not contain date provided in request and is unavailable in the DTO object.
     *
     * @param originAirportCode
     * @param destinationAirportCode
     * @param date
     * @return
     */
    public List<Flight> getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date) {
        return flightConnectionsResultMapper
                .mapToFlightConnectionsResult( skyScannerClient.getFlightConnections(originAirportCode, destinationAirportCode, date) )
                .getConnections().stream()
                    .filter(e -> e.getDepartureDate().equals(date))
                    .collect(Collectors.toList());
    }

    public List<Airport> getAirportsInCity(String city) {
        return cityAirportsResultMapper
                .mapToCityAirportsResult( skyScannerClient.getAirportsInCity(city) )
                .getAirports();
    }
}
