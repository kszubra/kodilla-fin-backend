package com.kodilla.kodillafinalbackend.skyscanner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CityAirportsResultMapper {
    private final AirportMapper airportMapper;

    public CityAirportsResult mapToCityAirportsResult(CityAirportsResultDto dto) {
        return new CityAirportsResult( airportMapper.mapToAirportList( dto.getAirports()) );
    }
}
