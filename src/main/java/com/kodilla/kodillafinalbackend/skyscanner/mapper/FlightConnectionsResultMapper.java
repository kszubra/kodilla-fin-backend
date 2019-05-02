package com.kodilla.kodillafinalbackend.skyscanner.mapper;

import com.kodilla.kodillafinalbackend.skyscanner.domain.FlightConnectionsResult;
import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.CarrierDto;
import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.FlightConnectionsResultDto;
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
