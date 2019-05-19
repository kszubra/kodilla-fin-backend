package com.kodilla.kodillafinalbackend.aop;

import com.kodilla.kodillafinalbackend.config.AdminConfig;
import com.kodilla.kodillafinalbackend.domain.*;
import com.kodilla.kodillafinalbackend.scheduler.NotificationScheduler;
import com.kodilla.kodillafinalbackend.scheduler.UpdateCountryDatabaseScheduler;
import com.kodilla.kodillafinalbackend.service.CountryService;
import com.kodilla.kodillafinalbackend.service.SimpleEmailService;
import com.kodilla.kodillafinalbackend.service.WeekendFlightOffersCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WatcherTest {
    @Autowired
    private NotificationScheduler scheduler;

    @Test
    @Transactional
    public void measureNotificationTime() {
        //Given
       scheduler.notifyAboutOffers();

       //then
    }
}