package br.com.harisson.core.model.enums;

public enum TransmissionType {
    AUTOMATIC("Automático"),
    MANUAL("Manual");

    private final String baseStringTransmissionType;

    TransmissionType(String baseStringTransmissionType) {
        this.baseStringTransmissionType = baseStringTransmissionType;
    }

    public String getTransmissionType() {
        return baseStringTransmissionType;
    }
}
