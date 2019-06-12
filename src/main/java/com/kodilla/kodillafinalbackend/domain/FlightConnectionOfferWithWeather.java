package com.kodilla.kodillafinalbackend.domain;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlightConnectionOfferWithWeather implements Comparable<FlightConnectionOfferWithWeather> {
    private Flight flight;
    private BigDecimal expectedTemperature;

    @Override
    public int compareTo(FlightConnectionOfferWithWeather other) {
        return this.getFlight().getMinPrice().compareTo(other.getFlight().getMinPrice());
    }

    @Override
    public String toString() {
        return "FlightConnectionOfferWithWeather{" +
                "flight=" + flight +
                ", expectedTemperature=" + expectedTemperature +
                '}';
    }
}
