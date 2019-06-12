package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.ServiceUsageRecord;
import com.kodilla.kodillafinalbackend.repository.ServiceUsageRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceUsageRecordService {
    private final ServiceUsageRecordRepository repository;

    @Transactional
    public void addRecord(final ServiceUsageRecord record) {
        repository.save(record);
    }

    public List<ServiceUsageRecord> getAllRecords(){
        return repository.findAll();
    }

    public List<ServiceUsageRecord> getRecordsOfMethod(final String className){
        return repository.findAllByServiceClass(className);
    }

    @Transactional
    public void deleteAllRecords() {
        repository.deleteAll();
    }
}
