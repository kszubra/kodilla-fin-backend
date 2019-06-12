package com.kodilla.kodillafinalbackend.external.api.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.CityAirportsResult;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.dto.CityAirportsResultDto;
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
