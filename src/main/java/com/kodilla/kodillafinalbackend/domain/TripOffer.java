package com.kodilla.kodillafinalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripOffer {
    private FlightConnectionOfferWithWeather thereConnection;
    private FlightConnectionOfferWithWeather returnConnection;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Trip: " +
                "your flight there: " + thereConnection +
                ", return flight: " + returnConnection +
                ", with total cost of: " + price.setScale(2, RoundingMode.HALF_EVEN) +
                '}';
    }
}
