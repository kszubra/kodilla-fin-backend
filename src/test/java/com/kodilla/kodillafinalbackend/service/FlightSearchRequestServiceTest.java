package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightSearchRequestServiceTest {
    @Autowired
    private FlightSearchRequestService service;

    @Test
    public void testAddSearchRequest() {
        //Given
        service.deleteAll();

        FlightSearchRequest testRequest = FlightSearchRequest.builder()
                .departureAirport( "WMI" )
                .departureCity( "Warsaw" )
                .destinationAirport( "MXP" )
                .destinationCity("Malpensa")
                .departureDay(LocalDate.now())
                .build();

        service.addSearchRequest(testRequest);

        //When
        FlightSearchRequest result = service.getAllRequests().get(0);

        //Then
        assertEquals(testRequest, result);
    }

    @Test
    public void testGetAllRequests() {
        service.deleteAll();

        FlightSearchRequest testRequest = FlightSearchRequest.builder()
                .departureAirport( "WMI" )
                .departureCity( "Warsaw" )
                .destinationAirport( "MXP" )
                .destinationCity("Malpensa")
                .departureDay(LocalDate.now())
                .build();
        FlightSearchRequest testRequestTwo = FlightSearchRequest.builder()
                .departureAirport( "MXP" )
                .departureCity( "Mediolan" )
                .destinationAirport( "WAW" )
                .destinationCity("Warsaw")
                .departureDay(LocalDate.now().plusDays(10))
                .build();
        List<FlightSearchRequest> requests = Arrays.asList(testRequest, testRequestTwo);

        service.addSearchRequest(testRequest);
        service.addSearchRequest(testRequestTwo);

        //When
        List<FlightSearchRequest> result = service.getAllRequests();

        //Then
        assertEquals(requests, result);
    }
}