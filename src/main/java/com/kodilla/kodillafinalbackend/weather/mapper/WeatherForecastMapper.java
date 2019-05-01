package com.kodilla.kodillafinalbackend.weather.mapper;

import com.kodilla.kodillafinalbackend.weather.domain.WeatherForecast;
import com.kodilla.kodillafinalbackend.weather.domain.dto.WeatherForecastDto;
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
