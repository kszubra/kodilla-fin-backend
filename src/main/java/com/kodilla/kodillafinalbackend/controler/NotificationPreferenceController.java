package com.kodilla.kodillafinalbackend.controler;

import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.dto.NotificationPreferenceCreationDto;
import com.kodilla.kodillafinalbackend.domain.dto.NotificationPreferenceDto;
import com.kodilla.kodillafinalbackend.domain.dto.NotificationPreferenceListDto;
import com.kodilla.kodillafinalbackend.mapper.NotificationPreferenceMapper;
import com.kodilla.kodillafinalbackend.mapper.UserMapper;
import com.kodilla.kodillafinalbackend.service.NotificationPreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping
@AllArgsConstructor
public class NotificationPreferenceController {
    private final NotificationPreferenceService preferenceService;
    private final NotificationPreferenceMapper preferenceMapper;
    private final UserMapper userMapper;

    @PostMapping("preferences")
    public void addPreference(@RequestBody NotificationPreferenceCreationDto dto) {
        preferenceService.addPreference( preferenceMapper.creationDtoToPreference( dto ) );
    }

    @PutMapping("preferences")
    @Transactional
    public NotificationPreferenceDto updatePreference(@RequestBody NotificationPreferenceDto updatingDto) {
        NotificationPreference preference = preferenceService.getPreferenceById( updatingDto.getId() );

        if(! preference.getDepartureCity().equals( updatingDto.getDepartureCity() )) {preference.setDepartureCity( updatingDto.getDepartureCity() );}
        if(! preference.getDestinationCity().equals( updatingDto.getDestinationCity() )) {preference.setDestinationCity( updatingDto.getDestinationCity() );}
        if(! preference.getUser().equals( userMapper.mapToUser( updatingDto.getUserDto() ) )) {preference.setUser( userMapper.mapToUser( updatingDto.getUserDto() ) );}
        if(! preference.getMaxPrice().equals( updatingDto.getMaxPrice() )) {preference.setMaxPrice( updatingDto.getMaxPrice() );}
        if(! preference.getMinTemperature().equals( updatingDto.getMinTemperature() )) {preference.setMinTemperature( updatingDto.getMinTemperature() );}

        return preferenceMapper.mapToPreferenceDto( preference );
    }

    @DeleteMapping("preferences/{id}")
    public void deletePreference(@PathVariable("id") Long id) {
        preferenceService.deletePreferenceById(id);
    }

    @GetMapping("preferences")
    public NotificationPreferenceListDto getPreferences() {
        return new NotificationPreferenceListDto( preferenceMapper.mapToPrefrenceDtoList( preferenceService.getAllPreferences() ) );
    }

    @GetMapping("preferences/")
    public NotificationPreferenceListDto getPreferencesByDestinationCity(@RequestParam("city") String city) {
        return new NotificationPreferenceListDto( preferenceMapper.mapToPrefrenceDtoList( preferenceService.getAllPreferencesByCity(city) ) );
    }

    @GetMapping("preferences/{id}")
    public NotificationPreferenceDto getPreference(@PathVariable("id") Long id) {
        return preferenceMapper.mapToPreferenceDto( preferenceService.getPreferenceById(id) );
    }
}
