package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
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
    private Set<NotificationPreference> notificationPreferences;

}
