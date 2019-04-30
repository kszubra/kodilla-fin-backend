package com.kodilla.kodillafinalbackend.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class WeatherForecast {
    private String city;
    private List<DailyWeatherForecast> dailyForecasts;
}
