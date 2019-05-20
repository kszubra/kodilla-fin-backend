package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.ServiceUsageRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceUsageRecordServiceTest {
    @Autowired
    ServiceUsageRecordService service;

    @Before
    public void clean() {
        service.deleteAllRecords();
    }

    @Test
    public void testAddRecord() {
        //Given
        ServiceUsageRecord testRecord = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Arguments" )
                .build();

        //When
        service.addRecord(testRecord);
        ServiceUsageRecord result = service.getAllRecords().get(0);

        //Then
        assertEquals(testRecord, result);
    }

    @Test
    public void getAllRecords() {
        //Given
        ServiceUsageRecord testRecord = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Arguments" )
                .build();
        ServiceUsageRecord testRecordTwo = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeOtherClass" )
                .methodArgument( "Something" )
                .build();

        List<ServiceUsageRecord> records = Arrays.asList(testRecord, testRecordTwo);
        service.addRecord(testRecord);
        service.addRecord(testRecordTwo);

        //When
        List<ServiceUsageRecord> result = service.getAllRecords();

        //Then
        assertEquals(records, result);
    }

    @Test
    public void getRecordsOfMethod() {
        //Given
        ServiceUsageRecord testRecord = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Arguments" )
                .build();
        ServiceUsageRecord testRecordTwo = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeOtherClass" )
                .methodArgument( "Something" )
                .build();
        ServiceUsageRecord testRecordThree = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Something" )
                .build();

        service.addRecord(testRecord);
        service.addRecord(testRecordTwo);
        service.addRecord(testRecordThree);

        //When
        List<ServiceUsageRecord> result = service.getRecordsOfMethod("SomeClass");

        //Then
        assertEquals(2, result.size());
        assertFalse(result.contains(testRecordTwo));
    }

    @Test
    public void deleteAllRecords() {
        //Given
        ServiceUsageRecord testRecord = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Arguments" )
                .build();
        ServiceUsageRecord testRecordTwo = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeOtherClass" )
                .methodArgument( "Something" )
                .build();
        ServiceUsageRecord testRecordThree = ServiceUsageRecord.builder()
                .whenExecuted( LocalDateTime.now() )
                .serviceClass( "SomeClass" )
                .methodArgument( "Something" )
                .build();

        service.addRecord(testRecord);
        service.addRecord(testRecordTwo);
        service.addRecord(testRecordThree);

        //When
        service.deleteAllRecords();
        List<ServiceUsageRecord> result = service.getAllRecords();

        //Then
        assertEquals(0, result.size());
    }
}