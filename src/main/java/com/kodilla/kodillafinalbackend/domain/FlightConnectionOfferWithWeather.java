package com.kodilla.kodillafinalbackend.domain;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlightConnectionOfferWithWeather {
    private Flight flight;
    private BigDecimal expectedTemperature;

    @Override
    public String toString() {
        return "FlightConnectionOfferWithWeather{" +
                "flight=" + flight +
                ", expectedTemperature=" + expectedTemperature +
                '}';
    }
}
