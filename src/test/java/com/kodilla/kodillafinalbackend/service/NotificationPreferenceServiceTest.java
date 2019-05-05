package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.exceptions.NotificationPreferenceNotFoundException;
import com.kodilla.kodillafinalbackend.repository.NotificationPreferenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationPreferenceServiceTest {
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;
    @Autowired
    private UserService userService;

    @Before
    public void cleanUp() {
        notificationPreferenceService.deleteAllPreferences();
        userService.deleteAllUsers();
    }

    @Test
    public void testAddPreference() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        Long id = testPreference.getId();
        NotificationPreference result = notificationPreferenceService.getPreferenceById(id);

        //Then
        assertEquals(testPreference, result);
        assertTrue( testUser.getNotificationPreferences().contains(testPreference) );
    }

    @Test
    public void testGetAllPreferences() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo32.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);
        userService.addUser(testUserTwo);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUserTwo)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(3, result.size());
        assertTrue( result.contains(testPreference) );
        assertTrue( result.contains(testPreferenceTwo) );
        assertTrue( result.contains(testPreferenceThree) );
    }

    @Test
    public void testGetAllPreferencesByCity() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(700.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByCity("Berlin");

        //Then
        assertEquals(2, result.size());
        assertFalse( result.contains(testPreference) );
    }

    @Test
    public void testGetAllPreferencesByUser() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo32.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);
        userService.addUser(testUserTwo);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Moscow")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUserTwo)
                .build();

        //When
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByUser(testUser);

        //Then
        assertEquals(2, result.size());
        assertFalse( result.contains(testPreferenceThree) );
    }

    @Test
    public void testDeleteAllPreferences() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Moscow")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUser)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);

        //When
        notificationPreferenceService.deleteAllPreferences();
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(0, result.size() );
    }

    @Test
    public void testDeleteAllPreferencesByUser() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        User testUserTwo = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo32.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);
        userService.addUser(testUserTwo);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Moscow")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUserTwo)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);

        //When
        notificationPreferenceService.deleteAllPreferencesByUser(testUser);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(1, result.size());
        assertTrue( result.contains(testPreferenceThree) );
    }

    @Test
    public void testDeletePreferenceById() {
        //Given
        User testUser = User.builder()
                .name("John")
                .surname("Rambo")
                .email("rambo@rambo.com")
                .registered(LocalDate.now())
                .securePassword("123456789")
                .notificationPreferences(new HashSet<>())
                .build();
        userService.addUser(testUser);

        NotificationPreference testPreference = NotificationPreference.builder()
                .city("Warsaw")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(300.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceTwo = NotificationPreference.builder()
                .city("Berlin")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(500.00))
                .user(testUser)
                .build();
        NotificationPreference testPreferenceThree = NotificationPreference.builder()
                .city("Moscow")
                .minTemperature(10)
                .maxPrice(BigDecimal.valueOf(900.00))
                .user(testUser)
                .build();
        notificationPreferenceService.addPreference(testPreference);
        notificationPreferenceService.addPreference(testPreferenceTwo);
        notificationPreferenceService.addPreference(testPreferenceThree);

        //When
        Long id = testPreference.getId();
        notificationPreferenceService.deletePreferenceById(id);
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferences();

        //Then
        assertEquals(2, result.size() );
        assertFalse( result.contains( testPreference ) );
    }

    @Test(expected= NotificationPreferenceNotFoundException.class)
    public void testGetPreferenceByNotExistingId() {
        //Given, When & Then
        NotificationPreference result = notificationPreferenceService.getPreferenceById(34L);
    }

    @Test
    public void testGetPreferenceByNotExistingUser() {
        //Given, When
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByUser(new User());

        //Then
        assertEquals(0, result.size());
    }

    @Test
    public void testGetPreferenceByNotExistingCity() {
        //Given, When
        List<NotificationPreference> result = notificationPreferenceService.getAllPreferencesByCity("jkdfsas");

        //Then
        assertEquals(0, result.size());
    }

    @Test(expected = NotificationPreferenceNotFoundException.class)
    public void testDeletePreferenceByNotExistingId() {
        //Given, When & Then
        notificationPreferenceService.deletePreferenceById(234L);
    }

    @Test
    public void testDeletePreferenceByNotExistingUser() {
        //Given, When & Then
        notificationPreferenceService.deleteAllPreferencesByUser(new User());
    }
}