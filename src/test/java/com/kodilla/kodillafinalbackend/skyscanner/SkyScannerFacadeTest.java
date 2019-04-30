package com.kodilla.kodillafinalbackend.skyscanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyScannerFacadeTest {
    @Autowired
    private SkyScannerFacade skyScannerFacade;

    @Test
    public void testGetFlightConnections() {
        //Given
        FlightConnectionsResult testConnections = skyScannerFacade.getFlightConnections("WAW", "HAJ", LocalDate.of(2019, 06, 07));

        //Then
        assertNotNull(testConnections.getConnections());
        assertTrue(testConnections.getConnections().size() > 0);


    }
}