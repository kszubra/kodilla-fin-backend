package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.SkyScannerFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeekendFlightOffersCreatorTest {
    @Autowired
    private WeekendFlightOffersCreator weekendFlightOffersCreator;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @After
    public void cleanUp() {
        notificationPreferenceService.deleteAllPreferences();
        userService.deleteAllUsers();
    }

    @Before
    public void prepareDatabase() {
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Hanover")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .departureCity("Warsaw")
                .destinationCity("Hanover")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(700.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .departureCity("Wrocław")
                .destinationCity("Gdańsk")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);

    }

    @Test
    public void testGetAllSearchRequests() {
        //When
        Set<FlightSearchRequest> result = weekendFlightOffersCreator.getAllSearchRequests();
        /**
         * WARSAW AIRPORTS: WAW, WMI
         * HANOVER AIRPORTS: HAJ
         * GDAŃSK AIRPORTS: GDN
         * WROCŁAW AIRPORTS: WRO
         *          |
         *          |
         *          V
         * Proper results:
         * WARSAW - HANOVER: 2 preferences, but same cities, so should be counted as one and not duplicate results
         *      WAW - HAJ (there) | HAJ - WAW (return)
         *      WMI - HAJ (there) | HAJ - WMI (return)
         *
         *      2 ways to go there, 2 ways to return: 4 search requests
         *
         * WROCLAW - GDANSK
         *      WRO - GDN (there) | GDN - WRO (return)
         *
         *      1 way to go there, 1 ways to return: 2 search requests
         *
         *      PROPER NUMBER OF SEARCH REQUESTS: 4 + 2 = 6
         *
         */

        //Then
        assertEquals(6, result.size());
        result.forEach(System.out::println);
    }

    @Test
    public void testGettingForecastForDestinationCities() {
        //When
        Map<String, Double> result = weekendFlightOffersCreator.getWeatherForDestinationCities();

        //Then
        assertTrue( result.containsKey("hanover") );
        assertTrue( result.containsKey("gdańsk") );
        for(Map.Entry<String, Double> entry : result.entrySet()) {
            System.out.println("City: " + entry.getKey().toUpperCase() + ", expected average temperate during next weekend: " + entry.getValue());
        }
    }

}