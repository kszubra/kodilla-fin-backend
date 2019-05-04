package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.exceptions.NotificationPreferenceNotFoundException;
import com.kodilla.kodillafinalbackend.repository.NotificationPreferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationPreferenceService {
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    public NotificationPreference addPreference(NotificationPreference preference) {
        return notificationPreferenceRepository.save(preference);
    }

    public NotificationPreference getPreferenceById(Long id) {
        return notificationPreferenceRepository.findById(id).orElseThrow(NotificationPreferenceNotFoundException::new);
    }

    public List<NotificationPreference> getAllPreferences() {
        return notificationPreferenceRepository.findAll();
    }

    /**
     * Returns a list of preferences with destination city chosen as city provided as method @param
     *
     * @param city
     * @return
     */
    public List<NotificationPreference> getAllPreferencesByCity(String city) {
        return notificationPreferenceRepository.findAllByCity(city);
    }

    /**
     * Returns a list of preferences created by user provided as @param
     *
     * @param user
     * @return
     */
    public List<NotificationPreference> getAllPreferencesByUser(User user) {
        return notificationPreferenceRepository.findAllByUser(user);
    }

    public void deleteAllPreferences() {
        notificationPreferenceRepository.deleteAll();
    }

    /**
     * Deletes all preferences created by user provided as @param
     *
     * @param user
     */
    public void deleteAllPreferencesByUser(User user) {
        notificationPreferenceRepository.deleteAllByUser(user);
    }

    public void deletePreferenceById(Long id) {
        notificationPreferenceRepository.deleteById(id);
    }

}
