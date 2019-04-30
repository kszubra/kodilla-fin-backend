package com.kodilla.kodillafinalbackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {

    @Value("${weather.api.key}")
    private String weatherApiKey;
}
