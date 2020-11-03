package br.com.harisson.springbackend.endpoint.controller;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.springbackend.endpoint.service.VehicleService;
import br.com.harisson.springbackend.endpoint.util.VehicleCreator;
import org.apache.commons.collections.IteratorUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class VehicleControllerTest {
    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleServiceMock;

    @BeforeEach
    public void setUp() {
        Iterable<Vehicle> vehiclePage = new PageImpl<>(List.of(VehicleCreator.createValidForSaleVehicle()));
        BDDMockito.when(vehicleServiceMock.listAllVehicles(any(Sort.class)))
                .thenReturn(vehiclePage);

        BDDMockito.when(vehicleServiceMock.findById(anyLong()))
                .thenReturn(VehicleCreator.createValidForSaleVehicle());

        BDDMockito.when(vehicleServiceMock.listVehiclesByModel(anyString()))
                .thenReturn(List.of(VehicleCreator.createValidForSaleVehicle()));

        BDDMockito.when(vehicleServiceMock.listVehiclesInStock())
                .thenReturn(List.of(VehicleCreator.createValidForSaleVehicle()));

        BDDMockito.when(vehicleServiceMock.listSoldVehicles())
                .thenReturn(List.of(VehicleCreator.createValidSoldVehicle()));

        BDDMockito.when(vehicleServiceMock.saveVehicle(VehicleCreator.createVehicleForSaleToBeSaved()))
                .thenReturn(VehicleCreator.createValidForSaleVehicle());

        BDDMockito.when(vehicleServiceMock.saveVehicle(VehicleCreator.createVehicleSoldToBeSaved()))
                .thenReturn(VehicleCreator.createValidSoldVehicle());

        BDDMockito.doNothing().when(vehicleServiceMock).deleteVehicleById(anyLong());

        BDDMockito.when(vehicleServiceMock.saveVehicle(VehicleCreator.createValidForSaleVehicle()))
                .thenReturn(VehicleCreator.createValidUpdateVehicleForSale());

        BDDMockito.when(vehicleServiceMock.saveVehicle(VehicleCreator.createValidSoldVehicle()))
                .thenReturn(VehicleCreator.createValidUpdateVehicleSold());
    }

    @Test
    @DisplayName("ListAll returns a iterable list of vehicles when successful")
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    void listAll_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        String expectedModel = VehicleCreator.createValidForSaleVehicle().getModel();

        Iterable<Vehicle> vehicleIterable = vehicleController.listAllVehicles(PageRequest.of(1, 1).getSort()).getBody();

        List<Vehicle> vehicleList = IteratorUtils.toList(vehicleIterable.iterator());

        Assertions.assertThat(vehicleIterable).isNotEmpty();

        Assertions.assertThat(vehicleList.get(0).getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("findVehicleById return an vehicle when successful")
    void findVehicleById_ReturnAnVehicleById_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidForSaleVehicle().getId();

        Vehicle vehicle = vehicleController.findVehicleById(1L).getBody();

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("listVehiclesByModel returns a iterable list of vehicles when successful")
    void listVehiclesByModel_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        String expectedModel = VehicleCreator.createValidForSaleVehicle().getModel();

        List<Vehicle> vehicleList = vehicleController.listVehiclesByModel("Gol").getBody();

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("listVehiclesInStock returns a iterable list of vehicles in stock when successful")
    void listVehiclesInStock_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        List<Vehicle> vehicleList = vehicleController.listVehiclesInStock().getBody();

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).isSold()).isFalse();
    }

    @Test
    @DisplayName("listSoldVehicles returns a iterable list of vehicles in stock when successful")
    void listSoldVehicles_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        List<Vehicle> vehicleList = vehicleController.listSoldVehicles().getBody();

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).isSold()).isTrue();
    }

    @Test
    @DisplayName("Save create an vehicle for sale when successful")
    void save_CreateVehicleForSale_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidForSaleVehicle().getId();

        Vehicle vehicleToBeSaved = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle vehicle = vehicleController.saveVehicle(vehicleToBeSaved).getBody();

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save create an vehicle sold when successful")
    void save_CreateVehicleSold_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidSoldVehicle().getId();

        Vehicle vehicleToBeSaved = VehicleCreator.createVehicleSoldToBeSaved();

        Vehicle vehicle = vehicleController.saveVehicle(vehicleToBeSaved).getBody();

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete removes the vehicle when successful")
    void delete_RemoveVehicle_WhenSuccessful() {
        ResponseEntity<Void> responseEntity = vehicleController.deleteVehicleById(1L);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Save updating update vehicle for sale when successful")
    void save_SaveUpdatedVehicleForSale_WhenSuccessful() {
        Vehicle validUpdateVehicleForSale = VehicleCreator.createValidUpdateVehicleForSale();

        String expectedModel = validUpdateVehicleForSale.getModel();

        Vehicle vehicle = vehicleController.saveVehicle(VehicleCreator.createValidForSaleVehicle()).getBody();

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("Save updating update vehicle sold when successful")
    void save_SaveUpdatedVehicleSold_WhenSuccessful() {
        Vehicle validUpdateVehicleSold = VehicleCreator.createValidUpdateVehicleSold();

        String expectedModel = validUpdateVehicleSold.getModel();

        Vehicle vehicle = vehicleController.saveVehicle(VehicleCreator.createValidSoldVehicle()).getBody();

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getModel()).isEqualTo(expectedModel);
    }
}
