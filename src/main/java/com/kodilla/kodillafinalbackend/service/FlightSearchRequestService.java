package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import com.kodilla.kodillafinalbackend.repository.FlightSearchRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightSearchRequestService {
    private final FlightSearchRequestRepository repository;

    @Transactional
    public void addSearchRequest(final FlightSearchRequest request) {
        repository.save(request);
    }

    public List<FlightSearchRequest> getAllRequests() {
        return repository.findAll();
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
