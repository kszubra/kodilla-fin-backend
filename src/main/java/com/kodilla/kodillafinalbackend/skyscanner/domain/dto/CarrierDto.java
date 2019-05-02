package com.kodilla.kodillafinalbackend.skyscanner.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierDto {

    @JsonProperty("CarrierId")
    private int carrierId;

    @JsonProperty("Name")
    private String carrierName;
}
