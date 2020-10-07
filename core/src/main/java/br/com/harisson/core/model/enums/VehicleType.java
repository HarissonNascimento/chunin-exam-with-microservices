package br.com.harisson.core.model.enums;

import java.nio.charset.StandardCharsets;

public enum VehicleType {
    TRUCK("Caminhões"),
    TRAILER_AND_BODYWORK("Carretas e Carrocerias"),
    CARS_AND_ULTILITARIES("Carros e Ultilitários"),
    MOTORCYCLES_AND_QUADRICYCLES("Motos e Quadriciclos"),
    NAUTICAL("Náuticos"),
    BUSES_AND_VANS("Ônibus e Vans"),
    OTHERS("Outros");

    private final String baseStringVehicleType;

    VehicleType(String baseStringVehicleType) {
        this.baseStringVehicleType = baseStringVehicleType;
    }

    public String getVehicleType() {
        return new String(baseStringVehicleType.getBytes(), StandardCharsets.UTF_8);
    }
}
