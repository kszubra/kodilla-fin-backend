package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRegistrationDto {
    private String name;
    private String surname;
    private String email;
    private String securePassword;
}
