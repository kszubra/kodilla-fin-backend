package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.MailSentRecord;
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
public class MailSentRecordServiceTest {
    @Autowired
    private MailSentRecordService service;

    @Before
    public void clean() {
        service.deleteAll();
    }

    @Test
    public void testAddRecord() {
        //Given
        MailSentRecord record = new MailSentRecord(LocalDateTime.now(), "mail@mail.com");

        //When
        service.addRecord(record);
        MailSentRecord result = service.getAllRecords().get(0);

        //Then
        assertEquals(record, result);
    }

    @Test
    public void testGetAllRecords() {
        //Given
        MailSentRecord recordOne = new MailSentRecord(LocalDateTime.now(), "mail@mail.com");
        MailSentRecord recordTwo = new MailSentRecord(LocalDateTime.now(), "mail222@mail.com");
        List<MailSentRecord> records = Arrays.asList(recordOne, recordTwo);

        service.addRecord(recordOne);
        service.addRecord(recordTwo);

        //When
        List<MailSentRecord> result = service.getAllRecords();

        //Then
        assertEquals(records, result);
    }

    @Test
    public void testDeleteAll() {
        //Given
        MailSentRecord recordOne = new MailSentRecord(LocalDateTime.now(), "mail@mail.com");
        MailSentRecord recordTwo = new MailSentRecord(LocalDateTime.now(), "mail222@mail.com");

        service.addRecord(recordOne);
        service.addRecord(recordTwo);

        //When
        service.deleteAll();
        List<MailSentRecord> result = service.getAllRecords();

        //Then
        assertEquals(0, result.size());
    }
}