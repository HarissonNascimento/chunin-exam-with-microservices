package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.model.Buyer;

public class BuyerCreator {
    public static Buyer createNonContactedBuyerToBeSaved() {
        return Buyer.builder()
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .build();
    }

    public static Buyer createContactedBuyerToBeSaved() {
        return Buyer.builder()
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .isContacted(true)
                .build();
    }

    public static Buyer createValidNonContactedBuyer() {
        return Buyer.builder()
                .id(1L)
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .build();
    }

    public static Buyer createValidContactedBuyer() {
        return Buyer.builder()
                .id(1L)
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .isContacted(true)
                .build();
    }

    public static Buyer createValidUpdateNonContactedBuyer() {
        return Buyer.builder()
                .id(1L)
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .build();
    }

    public static Buyer createValidUpdateContactedBuyer() {
        return Buyer.builder()
                .id(1L)
                .vehicle(VehicleCreator.createValidForSaleVehicle())
                .numberPhone("123456789")
                .name("Test Buyer")
                .isContacted(true)
                .build();
    }
}
