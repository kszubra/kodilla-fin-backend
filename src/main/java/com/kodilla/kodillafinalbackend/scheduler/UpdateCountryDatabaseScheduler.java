package com.kodilla.kodillafinalbackend.scheduler;

import com.kodilla.kodillafinalbackend.external.api.countries.RestcountriesFacade;
import com.kodilla.kodillafinalbackend.service.CountryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateCountryDatabaseScheduler {
    private final CountryService countryService;
    private final RestcountriesFacade restcountriesFacade;

    /**
     * Every 24 hours updates country database
     */
    @Scheduled(fixedDelay = 86400000)
    public void updateCountries() {
        log.info("Updating country database");
        countryService.updateDatabase( restcountriesFacade.getCountries() );
    }
}
