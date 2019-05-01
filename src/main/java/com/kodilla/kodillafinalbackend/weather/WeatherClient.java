package com.kodilla.kodillafinalbackend.weather;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.weather.domain.dto.WeatherForecastDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class WeatherClient {
    private final RestTemplate restTemplate;
    private final AdminConfig adminConfig;

    public WeatherForecastDto getWeatherForecast(String city){
        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getWeatherApiBaseUrl())
                .queryParam("key", adminConfig.getWeatherApiKey())
                .queryParam("lang", "en")
                .queryParam("units", "M")
                .queryParam("city", city)
                .build().encode().toUri();

        try{
            WeatherForecastDto response = restTemplate.getForObject(url, WeatherForecastDto.class);
            return Optional.ofNullable(response).orElse(new WeatherForecastDto());
        } catch(RestClientException e) {
            log.error(e.getMessage(), e);
            return new WeatherForecastDto();
        }

    }
}
