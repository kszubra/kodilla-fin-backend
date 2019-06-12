package com.kodilla.kodillafinalbackend.external.api.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.dto.FlightConnectionsResultDto;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.FlightConnectionsResult;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.dto.CarrierDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class FlightConnectionsResultMapper {
    private final FlightMapper flightMapper;

    public FlightConnectionsResult mapToFlightConnectionsResult(FlightConnectionsResultDto dto) {
        Map<Integer, String> carrierIdAndName = new HashMap<>();
        for(CarrierDto carrierDto : dto.getCarriers() ) {
            carrierIdAndName.put( carrierDto.getCarrierId(), carrierDto.getCarrierName() );
        }
        return new FlightConnectionsResult(
                flightMapper.mapToFlightList(dto.getFlights(), carrierIdAndName)
        );
    }
}
