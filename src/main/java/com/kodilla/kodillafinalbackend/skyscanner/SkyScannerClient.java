package com.kodilla.kodillafinalbackend.skyscanner;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.CityAirportsResultDto;
import com.kodilla.kodillafinalbackend.skyscanner.domain.dto.FlightConnectionsResultDto;
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
    private final AdminConfig adminConfig;

    /****
     * Request except uri requires sending header with X-Rapid host and key, because access to SkyScanner is done through rapidapi.com, not directly at SkyScanner
     */
    private HttpHeaders getSkyScannerHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", adminConfig.getSkyScannerApiHeaderHost());
        headers.add("X-RapidAPI-Key", adminConfig.getSkyScannerApiHeaderKey());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

    /***
     *
     * @param originAirportCode an IATA airport code of the departure airport
     * @param destinationAirportCode an IATA airport code of the destination airport
     * @param date date of your departure
     * @return
     */
    public FlightConnectionsResultDto getFlightConnections(String originAirportCode, String destinationAirportCode, LocalDate date){
        HttpHeaders headers = this.getSkyScannerHeader();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getSkyScannerApiBaseUrl())
                .path("browseroutes/v1.0/PL/PLN/pl-PL/")
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

    public CityAirportsResultDto getAirportsInCity(String city) {
        HttpHeaders headers = this.getSkyScannerHeader();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        URI url = UriComponentsBuilder.fromHttpUrl(adminConfig.getSkyScannerApiBaseUrl())
                .path("autosuggest/v1.0/PL/PLN/pl-PL/")
                .queryParam("query", city)
                .build().encode().toUri();

        try{
            CityAirportsResultDto response = restTemplate.exchange(url, HttpMethod.GET, entity, CityAirportsResultDto.class).getBody();
            return Optional.ofNullable(response).orElse(new CityAirportsResultDto());
        } catch(RestClientException e) {
            log.error(e.getMessage(), e);
            return new CityAirportsResultDto();
        }

    }

}
