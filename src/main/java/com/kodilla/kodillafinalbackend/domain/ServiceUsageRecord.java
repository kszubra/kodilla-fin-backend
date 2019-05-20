package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="SERVICE_USAGES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class ServiceUsageRecord {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String serviceClass;

    @NotNull
    private String methodArgument;

    @NotNull
    private LocalDateTime whenExecuted;

    @Override
    public String toString() {
        return "ServiceUsageRecord{" +
                "id=" + id +
                ", serviceClass='" + serviceClass + '\'' +
                ", methodArgument='" + methodArgument + '\'' +
                ", whenExecuted=" + whenExecuted +
                '}';
    }
}
