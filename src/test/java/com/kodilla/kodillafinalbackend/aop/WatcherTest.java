package com.kodilla.kodillafinalbackend.aop;

import com.kodilla.kodillafinalbackend.domain.ExecutionTimeRecord;
import com.kodilla.kodillafinalbackend.scheduler.NotificationScheduler;
import com.kodilla.kodillafinalbackend.service.ExecutionTimeRecordService;
import com.kodilla.kodillafinalbackend.service.ServiceUsageRecordService;
import com.kodilla.kodillafinalbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WatcherTest {
    @Autowired
    private NotificationScheduler scheduler;
    @Autowired
    ExecutionTimeRecordService executionTimeService;
    @Autowired
    ServiceUsageRecordService serviceUsageService;
    @Autowired
    UserService userService;

    @Test
    public void testMeasureNotificationTime() {
        //Given
        executionTimeService.deleteAllRecords();

        //When
        scheduler.notifyAboutOffers();
        List<ExecutionTimeRecord> times = executionTimeService.getAllRecords();

        //Then
        assertTrue(times.size() >= 1);
    }

}