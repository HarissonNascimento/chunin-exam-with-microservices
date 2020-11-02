package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.model.Vehicle;

import static br.com.harisson.core.model.enums.FuelType.FLEX;
import static br.com.harisson.core.model.enums.TransmissionType.AUTOMATIC;
import static br.com.harisson.core.model.enums.VehicleType.CARS_AND_ULTILITARIES;

public class VehicleCreator {
    public static Vehicle createVehicleForSaleToBeSaved(){
        return Vehicle.builder()
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .build();
    }

    public static Vehicle createVehicleSoldToBeSaved(){
        return Vehicle.builder()
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .isSold(true)
                .build();
    }

    public static Vehicle createValidForSaleVehicle(){
        return Vehicle.builder()
                .id(1L)
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .build();
    }

    public static Vehicle createValidSoldVehicle(){
        return Vehicle.builder()
                .id(1L)
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .isSold(true)
                .build();
    }

    public static Vehicle createValidUpdateVehicleForSale(){
        return Vehicle.builder()
                .id(1L)
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .build();
    }

    public static Vehicle createValidUpdateVehicleSold(){
        return Vehicle.builder()
                .id(1L)
                .price(20000.50)
                .year(2020)
                .vehicleType(CARS_AND_ULTILITARIES.getVehicleType())
                .fuelType(FLEX.getFuelType())
                .transmissionType(AUTOMATIC.getTransmissionType())
                .vehicleManufacturer("Volkswagen")
                .model("Gol")
                .description("Nice car for your family")
                .isSold(true)
                .build();
    }
}
