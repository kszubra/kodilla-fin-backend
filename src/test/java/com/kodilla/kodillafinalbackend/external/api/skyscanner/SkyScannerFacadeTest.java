package com.kodilla.kodillafinalbackend.external.api.skyscanner;

import com.kodilla.kodillafinalbackend.external.api.skyscanner.SkyScannerFacade;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyScannerFacadeTest {
    @Autowired
    private SkyScannerFacade skyScannerFacade;

    @Test
    public void testGetFlightConnections() {
        //Given
        List<Flight> testConnections = skyScannerFacade.getFlightConnections("WAW", "HAJ", LocalDate.of(2019, 06, 07));

        //Then
        assertNotNull(testConnections);
        assertTrue(testConnections.size() > 0);
        testConnections.stream()
                .forEach(e -> {
                    assertEquals(LocalDate.of(2019, 06, 07), e.getDepartureDate() );
                    System.out.println(e);
                } );
    }

    @Test
    public void testGetAirportsInCity() {
        //Given
        List<Airport> testAirports = skyScannerFacade.getAirportsInCity("Moscow");

        //Then
        assertNotNull(testAirports);
        assertTrue(testAirports.size() > 0);
        testAirports.stream()
                .forEach(e -> assertNotEquals("NOT AIRPORT", e.getAirportCode() ) );
    }

}