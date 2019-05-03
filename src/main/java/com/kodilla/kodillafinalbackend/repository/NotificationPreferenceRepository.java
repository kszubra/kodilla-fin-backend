package com.kodilla.kodillafinalbackend.repository;

import com.kodilla.kodillafinalbackend.external.api.restcountries.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.external.api.restcountries.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationPreferenceRepository extends CrudRepository<NotificationPreference, Long> {
    @Override
    NotificationPreference save(NotificationPreference preference);

    @Override
    Optional<NotificationPreference> findById(Long id);

    @Override
    List<NotificationPreference> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    void deleteAllByUser(User user);

    List<NotificationPreference> findAllByCity(String city);

    List<NotificationPreference> findAllByUser(User user);
}
