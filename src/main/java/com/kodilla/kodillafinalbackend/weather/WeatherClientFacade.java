package com.kodilla.kodillafinalbackend.weather;

import com.kodilla.kodillafinalbackend.weather.domain.WeatherForecast;
import com.kodilla.kodillafinalbackend.weather.mapper.WeatherForecastMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherClientFacade {
    private final WeatherClient weatherClient;
    private final WeatherForecastMapper weatherForecastMapper;

    public WeatherForecast getWeatherForecast(String city) {
        return weatherForecastMapper.mapToWeatherForecast( weatherClient.getWeatherForecast(city) );
    }
}
