package com.kodilla.kodillafinalbackend.repository;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSearchRequestRepository extends CrudRepository<FlightSearchRequest, Long> {
    @Override
    FlightSearchRequest save(FlightSearchRequest request);

    @Override
    List<FlightSearchRequest> findAll();

    @Override
    void deleteAll();
}
