package com.energy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RenewableProductionResponse {
    private String region;
    private String energyType;
    private Double totalProduction;
}