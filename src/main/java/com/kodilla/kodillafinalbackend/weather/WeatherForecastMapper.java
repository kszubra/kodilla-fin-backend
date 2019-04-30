package com.kodilla.kodillafinalbackend.weather;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherForecastMapper {
    private final DailyWeatherForecastMapper dailyWeatherForecastMapper;

    public WeatherForecast mapToWeatherForecast(WeatherForecastDto dto) {
        return WeatherForecast.builder()
                .city( dto.getCity() )
                .dailyForecasts( dailyWeatherForecastMapper.mapToDailyWeatherForecastList( dto.getWeatherForecasts() ) )
                .build();
    }
}
