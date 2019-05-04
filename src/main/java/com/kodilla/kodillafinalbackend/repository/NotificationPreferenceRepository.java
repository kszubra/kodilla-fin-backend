package com.kodilla.kodillafinalbackend.repository;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationPreferenceRepository extends CrudRepository<NotificationPreference, Long> {
    @Override
    NotificationPreference save(NotificationPreference preference);

    @Override
    List<NotificationPreference> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Long id);

    @Override
    Optional<NotificationPreference> findById(Long id);

    void deleteAllByUser(User user);

    List<NotificationPreference> findAllByCity(String city);

    List<NotificationPreference> findAllByUser(User user);
}
