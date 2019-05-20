package com.kodilla.kodillafinalbackend.aop;

import com.kodilla.kodillafinalbackend.domain.ExecutionTimeRecord;
import com.kodilla.kodillafinalbackend.service.ExecutionTimeRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class Watcher {
    private final ExecutionTimeRecordService service;

    /**
     * Measures how long it takes to perform scheduled sending emails for subscribed users
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */

    @Around("execution(* com.kodilla.kodillafinalbackend.scheduler.NotificationScheduler.notifyAboutOffers(..))")
    public Object measureNotificationTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        try{
            long begin = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            long executionTime = end - begin;
            log.info("Time consumed: " + executionTime + "[ms]");
            log.info("Saving time in database");
            ExecutionTimeRecord record = ExecutionTimeRecord.builder()
                    .method("notifyAboutOffers")
                    .whenExecuted( LocalDateTime.now() )
                    .executionTime( executionTime )
                    .build();

            service.addRecord( record );
            log.info("Added record = " + record.toString());
        } catch(Throwable throwable) {
            log.error(throwable.getMessage());
            throw throwable;
        }

        return result;
    }

    /**
     * Logs every usage of service classes
     *
     * @param input
     * @param object
     */
    @Before("execution(* com.kodilla.kodillafinalbackend.service..*.*(..))" +
            "&& args(input) && target(object)")
    public void logServiceUsage(Object input, Object object) {
        log.info("Class: " + object.getClass().getName() + ", Args: " + input.toString());
    }
}
