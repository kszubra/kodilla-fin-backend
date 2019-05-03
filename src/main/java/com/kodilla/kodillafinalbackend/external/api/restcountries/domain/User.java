package com.kodilla.kodillafinalbackend.external.api.restcountries.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @NotNull
    @Size(min=3, max=20, message="Value must be 3-20 characters long")
    @Column(name="NAME")
    private String name;

    @NotNull
    @Size(min=3, max=20, message="Value must be 3-20 characters long")
    @Column(name="SURNAME")
    private String surname;

    @NotNull
    @Email(message="Value must be a valid email address")
    @Column(name="EMAIL", unique = true)
    private String email;

    @NotNull
    @Size(min=8, max=20, message="Value must be 8-20 characters long")
    private String securePassword;

    @NotNull
    @Column(name="REGISTERED")
    private LocalDate registered;

    @NotNull
    @Column(name="NOTIFICATION_PREFERENCES")
    @OneToMany(
            targetEntity = NotificationPreference.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<NotificationPreference> notificationPreferences;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                surname.equals(user.surname) &&
                email.equals(user.email) &&
                securePassword.equals(user.securePassword) &&
                registered.equals(user.registered) &&
                notificationPreferences.equals(user.notificationPreferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, securePassword, registered, notificationPreferences);
    }
}
