package br.com.harisson.core.model.enums;

import java.nio.charset.StandardCharsets;

public enum TransmissionType {
    AUTOMATIC("Automático"),
    MANUAL("Manual");

    private final String baseStringTransmissionType;

    TransmissionType(String baseStringTransmissionType) {
        this.baseStringTransmissionType = baseStringTransmissionType;
    }

    public String getTransmissionType() {
        return new String(baseStringTransmissionType.getBytes(), StandardCharsets.UTF_8);
    }
}
