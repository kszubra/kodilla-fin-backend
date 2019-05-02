package com.kodilla.kodillafinalbackend.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.FlightDto;
import com.kodilla.kodillafinalbackend.skyscanner.domain.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    private List<String> mapToCarrierName(List<Integer> carrierIds, Map<Integer, String> carrierIdAndName) {
        return carrierIds.stream()
                .map(carrierIdAndName::get)
                .collect(Collectors.toList());
    }

    private Flight mapToFlight(FlightDto dto, Map<Integer, String> carrierIdAndName) {
        return Flight.builder()
                .carriers( this.mapToCarrierName(dto.getOutboundLeg().getCarriersIds(), carrierIdAndName ))
                .departureDate( LocalDate.parse(dto.getOutboundLeg().getDepartureDate().substring(0, 10) ))
                .minPrice( dto.getMinPrice() )
                .direct( dto.isDirect() )
                .build();
    }

    public List<Flight> mapToFlightList(List<FlightDto> dtoList, Map<Integer, String> carrierIdAndName) {
        return dtoList.stream()
                .map( dto -> mapToFlight(dto, carrierIdAndName) )
                .collect(Collectors.toList());
    }
}
