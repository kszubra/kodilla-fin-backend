package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.exceptions.CountryNotFoundException;
import com.kodilla.kodillafinalbackend.external.api.restcountries.domain.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Before
    public void cleanupDatabase() {
        countryService.deleteAllCountries();
    }

    @Test
    public void testAddCountry() {
        //Given
        Country testCountryOne = new Country(1L, "Poland", "Warsaw");
        Country testCountryTwo = new Country(2L, "Germany", "Berlin");
        Country testCountryThree = new Country(3L, "Russia", "Moscow");

        //When
        countryService.addCountry(testCountryOne);
        countryService.addCountry(testCountryTwo);
        countryService.addCountry(testCountryThree);
        List<Country> result = countryService.getAllCountries();

        //Then
        assertEquals(3, result.size());
        assertTrue(result.contains(testCountryOne));
        assertTrue(result.contains(testCountryTwo));
        assertTrue(result.contains(testCountryThree));
    }

    @Test
    public void testGetCountryById() {
        //Given
        Country testCountryOne = new Country();
        testCountryOne.setName("Poland");
        testCountryOne.setCapital("Warsaw");

        //When
        countryService.addCountry(testCountryOne);
        long id = testCountryOne.getId();
        Country result = countryService.getCountryById(id);

        //Then
        assertEquals(testCountryOne, result);
    }

    @Test
    public void testGetCountryByName() {
        //Given
        Country testCountryOne = new Country();
        testCountryOne.setName("Rwanda");
        testCountryOne.setCapital("Kigali");
        countryService.addCountry(testCountryOne);

        //When
        countryService.addCountry(testCountryOne);
        Country result = countryService.getCountryByName("Rwanda");

        //Then
        assertEquals(testCountryOne, result);
    }

    @Test
    public void testUpdateDatabase() {
        //Given
        Country testCountryOne = new Country(1L, "Poland", "Warsaw");
        Country testCountryTwo = new Country(2L, "Germany", "Berlin");
        Country testCountryThree = new Country(3L, "Russia", "Moscow");
        countryService.addCountry(testCountryOne);
        countryService.addCountry(testCountryTwo);
        countryService.addCountry(testCountryThree);

        List<Country> updatingList = Arrays.asList(
                new Country(1L, "Poland", "Warsaw"),
                new Country(2L, "Germany", "Berlin"),
                new Country(3L, "Russia", "Moscow"),
                new Country(4L, "France", "Paris"),
                new Country(5L, "Spain", "Madrid")
        );

        //When
        countryService.updateDatabase(updatingList);
        List<Country> result = countryService.getAllCountries();

        //Then
        assertEquals(5, result.size());
        result.stream()
                .forEach(e -> {
                        assertEquals(1, Collections.frequency(result, e) );
                });
    }

    @Test(expected = CountryNotFoundException.class)
    public void testGettingCountryByNotExistingId() {
        //Given
        Country testCountryOne = new Country(1L, "Poland", "Warsaw");

        //When & Then
        countryService.addCountry(testCountryOne);
        Country result = countryService.getCountryById(3L);
    }

    @Test(expected = CountryNotFoundException.class)
    public void testGettingCountryByNotExistingName() {
        //Given
        Country testCountryOne = new Country(1L, "Poland", "Warsaw");

        //When & Then
        countryService.addCountry(testCountryOne);
        Country result = countryService.getCountryByName("RPA");
    }
}