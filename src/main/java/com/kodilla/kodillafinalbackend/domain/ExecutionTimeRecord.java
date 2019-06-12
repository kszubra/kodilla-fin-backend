package com.kodilla.kodillafinalbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="EXECUTION_TIMES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class ExecutionTimeRecord {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String method;

    @NotNull
    private LocalDateTime whenExecuted;

    @NotNull
    private Long executionTime;

    @Override
    public String toString() {
        return "ExecutionTimeRecord{" +
                "id=" + id +
                ", method='" + method + '\'' +
                ", whenExecuted=" + whenExecuted +
                ", executionTime=" + executionTime +
                '}';
    }
}
