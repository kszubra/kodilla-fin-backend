package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String securePassword;
    private LocalDate registered;
    private Set<Long> notificationIds;

}
