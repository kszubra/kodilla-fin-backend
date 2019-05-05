package com.kodilla.kodillafinalbackend.external.api.countries;

import com.kodilla.kodillafinalbackend.external.api.countries.domain.Country;
import com.kodilla.kodillafinalbackend.external.api.countries.mapper.CountryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestcountriesFacade {
    private final RestcountriesClient restcountriesClient;
    private final CountryMapper countryMapper;

    public List<Country> getCountries() {
        return countryMapper.mapToCountryList( restcountriesClient.getResponse() );
    }
}
