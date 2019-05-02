package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.exceptions.CountryNotFoundException;
import com.kodilla.kodillafinalbackend.external.api.restcountries.domain.Country;
import com.kodilla.kodillafinalbackend.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    /**
     * Method checks if country object exists in database by its name.
     *
     * @param country country object which existence in database is checked.
     * @return true if @param country exists in database
     */
    private Boolean exists(Country country) {
        return countryRepository.existsByName( country.getName() );
    }

    /**
     * Method checks if country object exists in database by its name.
     *
     * @param country country object which existence in database is checked.
     * @return true if @param country DOES NOT exist in database
     */
    private Boolean notExists(Country country) {
        return !countryRepository.existsByName( country.getName() );
    }

    /**
     * Checks if @param country exists already in database. If exists, returns new empty country.
     * If doesn't exist, saves in in the databse
     *
     * @param country country object to be added to the database
     * @return
     */
    public Country addCountry(final Country country) {
        if( this.exists(country) ) {
            return new Country();
        }
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(final Long id) {
        return countryRepository.findById(id).orElseThrow(CountryNotFoundException::new);
    }

    public Country getCountryByName(final String name) {
        return countryRepository.findByName(name).orElseThrow(CountryNotFoundException::new);
    }

    /**
     * Method used to update database with a list of countries. Countries not existing in the database will be added.
     * Despite checking country existence in addCountry(), method does not attempt it on every list element to avoid
     * returning too many objects.
     *
      * @param countries list of countries to update database with
     */
    public void updateDatabase(final List<Country> countries) {
        countries.stream()
                .filter(this::notExists)
                .forEach(this::addCountry);
    }

    public void deleteAllCountries() {
        countryRepository.deleteAll();
    }
}
