package com.kodilla.kodillafinalbackend.skyscanner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Airport {
    private String airportCode;
    private String airportName;
    private String country;

    @Override
    public String toString() {
        return "Airport{" +
                "airportCode='" + airportCode + '\'' +
                ", airportName='" + airportName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
