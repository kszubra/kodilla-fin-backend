package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="NOTIFICATION_PREFERENCES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class NotificationPreference {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @NotNull
    @Column(name="CITY")
    @Min(value = 2, message = "City name must be at least 2 characters long")
    private String city;

    @NotNull
    @Column(name="MINIMUM_TEMPERATURE_CELSIUS")
    private Integer minTemperature;

    @NotNull
    @Column(name="MAXIMUM_PRICE_PLN")
    private BigDecimal maxPrice;
}
