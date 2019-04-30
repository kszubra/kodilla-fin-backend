package com.kodilla.kodillafinalbackend.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDto {

    @JsonProperty("MinPrice")
    private int minPrice;

    @JsonProperty("Direct")
    private boolean direct;

    @JsonProperty("OutboundLeg")
    private OutboundLegDto outboundLeg;
}
