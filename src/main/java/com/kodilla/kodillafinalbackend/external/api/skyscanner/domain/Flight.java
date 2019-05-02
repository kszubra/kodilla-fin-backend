package com.kodilla.kodillafinalbackend.external.api.skyscanner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Flight {
    private int minPrice;
    private boolean direct;
    private List<String> carriers;
    private LocalDate departureDate;

    @Override
    public String toString() {
        return "Flight{" +
                "minPrice=" + minPrice +
                ", direct=" + direct +
                ", carriers=" + carriers +
                ", departureDate=" + departureDate +
                '}';
    }
}
