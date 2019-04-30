package com.kodilla.kodillafinalbackend.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class DailyWeatherForecast {
    private double minTemperature;
    private double maxTemperature;
    private double expectedTemperature;
    private LocalDate date;

    @Override
    public String toString() {
        return "DailyWeatherForecast{" +
                "date=" + date +
                "minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", expectedTemperature=" + expectedTemperature +
                '}';
    }
}
