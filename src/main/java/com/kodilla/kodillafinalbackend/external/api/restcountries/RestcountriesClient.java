package com.kodilla.kodillafinalbackend.external.api.restcountries;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class RestcountriesClient {
    private final RestTemplate restTemplate;

    /**
     * API returns bare Object class object that is an ArrayList< Map<String, String> >. Every map represents one country
     * and entries represent information about country. For example set: key="capital" value="Warsaw". Mappers are
     * responsible for turning returned object to usable form.
     *
     * @return
     */
    public Object getResponse(){
        URI url = UriComponentsBuilder.fromHttpUrl("https://restcountries.eu/rest/v2/all")
                .build().encode().toUri();

        try{
            Object response =  restTemplate.getForObject(url, Object.class);
            return Optional.ofNullable(response).orElse(new Object() );
        } catch(RestClientException e) {
            log.error(e.getMessage(), e);
            return new Object();
        }

    }
}
