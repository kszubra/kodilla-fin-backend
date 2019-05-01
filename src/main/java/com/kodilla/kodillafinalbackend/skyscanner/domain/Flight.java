package com.kodilla.kodillafinalbackend.skyscanner.domain;

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
    private List<Integer> carriersIds;
    private LocalDate departureDate;

    @Override
    public String toString() {
        return "Flight{" +
                "minPrice=" + minPrice +
                ", direct=" + direct +
                ", carriersIds=" + carriersIds +
                ", departureDate=" + departureDate +
                '}';
    }
}
