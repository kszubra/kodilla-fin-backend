package com.kodilla.kodillafinalbackend.external.api.restcountries;

import com.kodilla.kodillafinalbackend.external.api.countries.RestcountriesFacade;
import com.kodilla.kodillafinalbackend.external.api.countries.domain.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestcountriesFacadeTest {
    @Autowired
    private RestcountriesFacade restcountriesFacade;

    @Test
    public void getCountries() {
        //Given
        List<Country> testCountries = restcountriesFacade.getCountries();

        //Then
        assertNotNull(testCountries);
        assertTrue(testCountries.size() > 0);
    }
}