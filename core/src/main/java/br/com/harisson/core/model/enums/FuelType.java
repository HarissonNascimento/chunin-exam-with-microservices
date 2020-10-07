package br.com.harisson.core.model.enums;

import java.nio.charset.StandardCharsets;

public enum FuelType {
    ETHANOL("Etanol"),
    DIESEL("Diesel"),
    ELECTRIC("Elétrico"),
    FLEX("Flex"),
    GASOLINE("Gasolina"),
    HYBRID("Híbrido"),
    OTHERS("Outros");

    private final String baseStringFuelType;

    FuelType(String fuelType) {
        this.baseStringFuelType = fuelType;
    }

    public String getFuelType() {
        return new String(baseStringFuelType.getBytes(), StandardCharsets.UTF_8);
    }
}
