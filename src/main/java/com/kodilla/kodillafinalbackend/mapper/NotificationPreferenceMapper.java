package com.kodilla.kodillafinalbackend.mapper;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.dto.NotificationPreferenceCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.NotificationPreferenceDto;
import com.kodilla.kodillafinalbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class NotificationPreferenceMapper {
    private final UserMapper userMapper;
    private final UserService userService;

    public NotificationPreference creationDtoToPreference(NotificationPreferenceCreationDto dto) {
        return NotificationPreference.builder()
                .id( dto.getId() )
                .departureCity( dto.getDepartureCity() )
                .destinationCity( dto.getDestinationCity() )
                .maxPrice( dto.getMaxPrice() )
                .minTemperature( dto.getMinTemperature() )
                .user( userService.getUserById( dto.getUserId() ) )
                .build();

    }

    public NotificationPreference mapToPreference(NotificationPreferenceDto dto) {
        return NotificationPreference.builder()
                .id( dto.getId() )
                .departureCity( dto.getDepartureCity() )
                .destinationCity( dto.getDestinationCity() )
                .maxPrice( dto.getMaxPrice() )
                .minTemperature( dto.getMinTemperature() )
                .user( userMapper.mapToUser( dto.getUserDto() ) )
                .build();
    }

    public List<NotificationPreference> mapToPreferenceList (List<NotificationPreferenceDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToPreference)
                .collect(Collectors.toList());
    }

    public NotificationPreferenceDto mapToPreferenceDto(NotificationPreference preference) {
        return NotificationPreferenceDto.builder()
                .id( preference.getId() )
                .departureCity( preference.getDepartureCity() )
                .destinationCity( preference.getDestinationCity() )
                .maxPrice( preference.getMaxPrice() )
                .minTemperature( preference.getMinTemperature() )
                .userDto( userMapper.mapToDto( preference.getUser() ) )
                .build();
    }

    public List<NotificationPreferenceDto> mapToPrefrenceDtoList(List<NotificationPreference> preferenceList) {
        return preferenceList.stream()
                .map(this::mapToPreferenceDto)
                .collect(Collectors.toList());
    }
}
