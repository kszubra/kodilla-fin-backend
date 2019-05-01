package com.kodilla.kodillafinalbackend.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.FlightDto;
import com.kodilla.kodillafinalbackend.skyscanner.domain.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    private Flight mapToFlight(FlightDto dto) {
        return Flight.builder()
                .carriersIds( dto.getOutboundLeg().getCarriersIds() )
                .departureDate( LocalDate.parse(dto.getOutboundLeg().getDepartureDate().substring(0, 10) ))
                .minPrice( dto.getMinPrice() )
                .direct( dto.isDirect() )
                .build();
    }

    public List<Flight> mapToFlightList(List<FlightDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToFlight)
                .collect(Collectors.toList());
    }
}
