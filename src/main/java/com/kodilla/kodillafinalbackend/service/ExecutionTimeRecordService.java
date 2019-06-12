package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.ExecutionTimeRecord;
import com.kodilla.kodillafinalbackend.repository.ExecutionTimeRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExecutionTimeRecordService {
    private final ExecutionTimeRecordRepository repository;

    public void addRecord(final ExecutionTimeRecord record) {
        repository.save(record);
    }

    public List<ExecutionTimeRecord> getAllRecords(){
        return repository.findAll();
    }

    public List<ExecutionTimeRecord> getRecordsOfMethod(final String method){
        return repository.findAllByMethod(method);
    }

    public void deleteAllRecords() {
        repository.deleteAll();
    }
}
