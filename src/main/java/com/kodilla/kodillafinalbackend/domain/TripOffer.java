package com.kodilla.kodillafinalbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
        return "TripOffer{" +
                "thereConnection=" + thereConnection +
                ", returnConnection=" + returnConnection +
                ", price=" + price +
                '}';
    }
}
