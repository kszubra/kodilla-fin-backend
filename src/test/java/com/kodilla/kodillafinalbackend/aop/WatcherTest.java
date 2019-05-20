package com.kodilla.kodillafinalbackend.aop;

import com.kodilla.kodillafinalbackend.scheduler.NotificationScheduler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WatcherTest {
    @Autowired
    private NotificationScheduler scheduler;

    @Test
    @Transactional
    public void testMeasureNotificationTime() {
        //Given
       scheduler.notifyAboutOffers();

       //then
    }

}