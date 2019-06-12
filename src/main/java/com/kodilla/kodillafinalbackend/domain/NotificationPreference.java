package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(name="NOTIFICATION_PREFERENCES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NotificationPreference {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @NotNull
    @Size(min = 2, message = "City name must be at least 2 characters long")
    private String departureCity;

    @NotNull
    @Size(min = 2, message = "City name must be at least 2 characters long")
    private String destinationCity;

    @NotNull
    private Integer minTemperature;

    @NotNull
    private BigDecimal maxPrice;

    @Override
    public String toString() {
        return "NotificationPreference{" +
                "id=" + id +
                ", departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", minTemperature=" + minTemperature +
                ", maxPrice=" + maxPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationPreference that = (NotificationPreference) o;
        return id.equals(that.id) &&
                user.equals(that.user) &&
                departureCity.equals(that.departureCity) &&
                destinationCity.equals(that.destinationCity) &&
                minTemperature.equals(that.minTemperature) &&
                maxPrice.setScale(2, RoundingMode.HALF_EVEN)
                        .equals(that.maxPrice.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, departureCity, destinationCity, minTemperature, maxPrice);
    }
}
