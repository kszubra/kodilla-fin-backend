package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.MailSentRecord;
import com.kodilla.kodillafinalbackend.repository.MailSentRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MailSentRecordService {
    private final MailSentRecordRepository repository;

    public MailSentRecord addRecord(final MailSentRecord record) {
        return repository.save(record);
    }

    public List<MailSentRecord> getAllRecords() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
