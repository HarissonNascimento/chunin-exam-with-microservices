package br.com.harisson.springbackend.endpoint.repository;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.util.VehicleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static br.com.harisson.core.model.enums.FuelType.GASOLINE;
import static br.com.harisson.core.model.enums.TransmissionType.MANUAL;
import static br.com.harisson.core.model.enums.VehicleType.MOTORCYCLES_AND_QUADRICYCLES;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Vehicle Repository Tests")
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @DisplayName("Save create vehicle when successful")
    void save_PersistVehicle_WhenSuccessful() {
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        Assertions.assertThat(savedVehicle.getId()).isNotNull();
        Assertions.assertThat(savedVehicle.getPrice()).isNotNull();
        Assertions.assertThat(savedVehicle.getPrice()).isEqualTo(vehicle.getPrice());
        Assertions.assertThat(savedVehicle.getYear()).isNotNegative();
        Assertions.assertThat(savedVehicle.getYear()).isEqualTo(vehicle.getYear());
        Assertions.assertThat(savedVehicle.getVehicleType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getVehicleType()).isEqualTo(vehicle.getVehicleType());
        Assertions.assertThat(savedVehicle.getFuelType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getFuelType()).isEqualTo(vehicle.getFuelType());
        Assertions.assertThat(savedVehicle.getTransmissionType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getTransmissionType()).isEqualTo(vehicle.getTransmissionType());
        Assertions.assertThat(savedVehicle.getVehicleManufacturer()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getVehicleManufacturer()).isEqualTo(vehicle.getVehicleManufacturer());
        Assertions.assertThat(savedVehicle.getModel()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getModel()).isEqualTo(vehicle.getModel());
        Assertions.assertThat(savedVehicle.getDescription()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getDescription()).isEqualTo(vehicle.getDescription());
    }

    @Test
    @DisplayName("Save update vehicle when successful")
    void save_UpdateVehicle_WhenSuccessful() {
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        savedVehicle.setPrice(10000.50);
        savedVehicle.setYear(2000);
        savedVehicle.setVehicleType(MOTORCYCLES_AND_QUADRICYCLES.getVehicleType());
        savedVehicle.setFuelType(GASOLINE.getFuelType());
        savedVehicle.setTransmissionType(MANUAL.getTransmissionType());
        savedVehicle.setVehicleManufacturer("Honda");
        savedVehicle.setModel("CB 300");
        savedVehicle.setDescription("Nice motorcycle for you");

        Vehicle updateVehicle = this.vehicleRepository.save(savedVehicle);

        Assertions.assertThat(savedVehicle.getId()).isNotNull();
        Assertions.assertThat(savedVehicle.getPrice()).isNotNull();
        Assertions.assertThat(savedVehicle.getPrice()).isEqualTo(updateVehicle.getPrice());
        Assertions.assertThat(savedVehicle.getYear()).isNotNegative();
        Assertions.assertThat(savedVehicle.getYear()).isEqualTo(updateVehicle.getYear());
        Assertions.assertThat(savedVehicle.getVehicleType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getVehicleType()).isEqualTo(updateVehicle.getVehicleType());
        Assertions.assertThat(savedVehicle.getFuelType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getFuelType()).isEqualTo(updateVehicle.getFuelType());
        Assertions.assertThat(savedVehicle.getTransmissionType()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getTransmissionType()).isEqualTo(updateVehicle.getTransmissionType());
        Assertions.assertThat(savedVehicle.getVehicleManufacturer()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getVehicleManufacturer()).isEqualTo(updateVehicle.getVehicleManufacturer());
        Assertions.assertThat(savedVehicle.getModel()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getModel()).isEqualTo(updateVehicle.getModel());
        Assertions.assertThat(savedVehicle.getDescription()).isNotEmpty();
        Assertions.assertThat(savedVehicle.getDescription()).isEqualTo(updateVehicle.getDescription());
    }

    @Test
    @DisplayName("Delete remove vehicle when successful")
    void delete_RemoveVehicle_WhenSuccessful(){
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        this.vehicleRepository.delete(vehicle);

        Vehicle vehicleById = this.vehicleRepository.findVehicleById(savedVehicle.getId());

        Assertions.assertThat(vehicleById).isNull();
    }

    @Test
    @DisplayName("Find by model returns vehicle list when successful")
    void listVehiclesByModel_ReturnVehicles_WhenSuccessful(){
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        String model = savedVehicle.getModel();

        List<Vehicle> vehicleList = this.vehicleRepository.listVehiclesByModel(model);

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();
        Assertions.assertThat(vehicleList).contains(savedVehicle);
    }

    @Test
    @DisplayName("Find by model returns empty list when no vehicle is found")
    void listVehiclesByModel_ReturnEmptyList_WhenVehicleNotFound(){
        String model = "Fake model";

        List<Vehicle> vehicleList = this.vehicleRepository.listVehiclesByModel(model);

        Assertions.assertThat(vehicleList).isEmpty();
    }

    @Test
    @DisplayName("List vehicles for sale returns vehicle list when successful")
    void listVehiclesInStock_ReturnVehicles_WhenSuccessful(){
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        List<Vehicle> vehicleList = this.vehicleRepository.listVehiclesInStock();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();
        Assertions.assertThat(vehicleList).contains(savedVehicle);
        Assertions.assertThat(savedVehicle.isSold()).isFalse();
    }

    @Test
    @DisplayName("List vehicles for sale returns empty list when no vehicles is found")
    void listVehiclesInStock_ReturnEmptyList_WhenVehiclesNotFound(){
        Vehicle vehicle = VehicleCreator.createVehicleSoldToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        List<Vehicle> vehicleList = this.vehicleRepository.listVehiclesInStock();

        Assertions.assertThat(savedVehicle.isSold()).isTrue();
        Assertions.assertThat(vehicleList.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("List sold vehicles returns vehicle list when successful")
    void listSoldVehicles_ReturnVehicles_WhenSuccessful(){
        Vehicle vehicle = VehicleCreator.createVehicleSoldToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        List<Vehicle> vehicleList = this.vehicleRepository.listSoldVehicles();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();
        Assertions.assertThat(vehicleList).contains(savedVehicle);
        Assertions.assertThat(savedVehicle.isSold()).isTrue();
    }

    @Test
    @DisplayName("List sold vehicles returns empty list when no vehicles is found")
    void listSoldVehicles_ReturnEmptyList_WhenVehiclesNotFound(){
        Vehicle vehicle = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        List<Vehicle> vehicleList = this.vehicleRepository.listSoldVehicles();

        Assertions.assertThat(savedVehicle.isSold()).isFalse();
        Assertions.assertThat(vehicleList.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when values is empty")
    void save_ThrowConstraintViolationException_WhenValuesIsEmpty(){
        Vehicle vehicle = new Vehicle();

        Assertions.assertThatThrownBy(() -> vehicleRepository.save(vehicle))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
