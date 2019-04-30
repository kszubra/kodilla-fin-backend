package com.kodilla.kodillafinalbackend.skyscanner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class SkyScannerClient {
    private final RestTemplate restTemplate;

    private final String uri = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseroutes/v1.0/PL/PLN/pl-PL";

    public FlightConnectionsResultDto getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date){

        /****
         * Request except uri requires sending header with X-Rapid host and key, because access to SkyScanner is done through rapidapi.com, not directly at SkyScanner
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
        headers.add("X-RapidAPI-Key", "61982aa7b9msh105c0da561e3ef5p12e7abjsn538c492aae0f");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        URI url = UriComponentsBuilder.fromHttpUrl(uri)
                .pathSegment(originAirportCode)
                .pathSegment(destinationAirportCode)
                .pathSegment(date.toString())
                .build().encode().toUri();



        try{
            FlightConnectionsResultDto response = restTemplate.exchange(url, HttpMethod.GET, entity, FlightConnectionsResultDto.class).getBody();
            return Optional.ofNullable(response).orElse(new FlightConnectionsResultDto());
        } catch(RestClientException e) {
            log.error(e.getMessage(), e);
            return new FlightConnectionsResultDto();
        }

    }
}
