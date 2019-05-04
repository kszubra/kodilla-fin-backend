package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    private Long id;

    @Size(min=3, max=20, message="Surname must be 3-20 characters long")
    private String name;

    @NotNull
    @Size(min=3, max=20, message="Surname must be 3-20 characters long")
    private String surname;

    @NotNull
    @Pattern(regexp = ".{3,}@.{2,}\\..{2,3}", message = "email format is not proper")
    @Column(unique = true)
    private String email;

    @NotNull
    private String securePassword;

    @NotNull
    private LocalDate registered;

    @NotNull
    @Column(name="NOTIFICATION_PREFERENCES")
    @OneToMany(
            targetEntity = NotificationPreference.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<NotificationPreference> notificationPreferences;

}
