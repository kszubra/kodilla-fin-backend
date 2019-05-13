package com.kodilla.kodillafinalbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NotNull
@Getter
public class NotificationPreferenceListDto {
    private List<NotificationPreferenceDto> preferences;
}
