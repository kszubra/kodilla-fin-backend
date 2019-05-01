package com.kodilla.kodillafinalbackend.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.skyscanner.domain.CityAirportsResult;
import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.CityAirportsResultDto;
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
