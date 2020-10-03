package br.com.harisson.core.model.enums;

public enum FuelType {
    ALCOHOL("Álcool"),
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
        return baseStringFuelType;
    }
}
