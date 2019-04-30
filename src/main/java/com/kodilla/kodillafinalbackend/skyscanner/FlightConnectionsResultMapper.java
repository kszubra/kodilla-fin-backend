package com.kodilla.kodillafinalbackend.skyscanner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlightConnectionsResultMapper {
    private final FlightMapper flightMapper;

    public FlightConnectionsResult mapToFlightConnectionsResult(FlightConnectionsResultDto dto) {
        return new FlightConnectionsResult(
                flightMapper.mapToFlightList(dto.getFlights())
        );
    }
}
