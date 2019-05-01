package com.kodilla.kodillafinalbackend.weather;

import com.kodilla.kodillafinalbackend.weather.domain.WeatherForecast;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherClientFacadeTest {
    @Autowired
    private WeatherClientFacade weatherClientFacade;

    @Test
    public void testGetWeatherForecast() {
        //Given
        WeatherForecast testForecast = weatherClientFacade.getWeatherForecast("Warsaw");

        //Then
        assertNotNull(testForecast);
        assertEquals("Warsaw", testForecast.getCity());
        assertEquals(16, testForecast.getDailyForecasts().size());
        System.out.println( testForecast.getDailyForecasts().get(3).toString() );
    }
}