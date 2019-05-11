package com.kodilla.kodillafinalbackend.mapper;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.domain.dto.UserDto;
import com.kodilla.kodillafinalbackend.domain.dto.UserRegistrationDto;
import com.kodilla.kodillafinalbackend.service.NotificationPreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    private final NotificationPreferenceService notificationService;

    public User mapRegistrationDtoToUser(final UserRegistrationDto dto) {
        return User.builder()
                .name( dto.getName() )
                .surname( dto.getSurname() )
                .email( dto.getEmail() )
                .registered( LocalDate.now() )
                .securePassword( dto.getSecurePassword() )
                .notificationPreferences( new HashSet<>())
                .build();
    }

    public User mapToUser (final UserDto dto) {
        return User.builder()
                .id( dto.getId() )
                .name( dto.getName() )
                .surname( dto.getSurname() )
                .email( dto.getEmail() )
                .registered( dto.getRegistered() )
                .securePassword( dto.getSecurePassword() )
                .notificationPreferences(
                        dto.getNotificationIds().stream()
                            .map(notificationService::getPreferenceById)
                            .collect(Collectors.toSet())

                )
                .build();
    }

    public UserDto mapToDto(final User user) {
        return UserDto.builder()
                .id( user.getId() )
                .name( user.getName() )
                .surname( user.getSurname() )
                .email( user.getEmail() )
                .securePassword( user.getSecurePassword() )
                .registered( user.getRegistered() )
                .notificationIds(
                        user.getNotificationPreferences().stream()
                            .map(NotificationPreference::getId)
                            .collect(Collectors.toSet())
                )
                .build();
    }

    public List<User> mapToUserList(final List<UserDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> mapToUserDtoList(final List<User> dtoList) {
        return dtoList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
