package com.kodilla.kodillafinalbackend.external.api.skyscanner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CityAirportsResult {
    private List<Airport> airports;
}
