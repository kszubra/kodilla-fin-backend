package com.kodilla.kodillafinalbackend.external.api.restcountries.mapper;

import com.kodilla.kodillafinalbackend.external.api.restcountries.domain.Country;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CountryMapper {

    private Country mapToCountry(Map<String, String> response) {
        return new Country(response.get("name"), response.get("capital"));
    }

    public List<Country> mapToCountryList(Object  response) {
        ArrayList<Map<String, String>> responseList = (ArrayList<Map<String, String>>)response;
        List<Country> countriesToReturn = new ArrayList<>();

        for( Map<String, String> countryMap : responseList ) {
            countriesToReturn.add( this.mapToCountry(countryMap) );
        }

        return countriesToReturn;
    }
}
