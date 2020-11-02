package br.com.harisson.springbackend.endpoint.service;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.exception.ResourceNotFoundException;
import br.com.harisson.springbackend.endpoint.util.VehicleCreator;
import br.com.harisson.springbackend.endpoint.util.VehicleUtil;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepositoryMock;

    @Mock
    private VehicleUtil vehicleUtilMock;

    @BeforeEach
    public void setUp() {
        Iterable<Vehicle> vehiclePage = new PageImpl<>(List.of(VehicleCreator.createValidForSaleVehicle()));
        BDDMockito.when(vehicleRepositoryMock.findAll(any(Sort.class)))
                .thenReturn(vehiclePage);

        BDDMockito.when(vehicleRepositoryMock.findVehicleById(anyLong()))
                .thenReturn(VehicleCreator.createValidForSaleVehicle());

        BDDMockito.when(vehicleRepositoryMock.listVehiclesByModel(anyString()))
                .thenReturn(List.of(VehicleCreator.createValidForSaleVehicle()));

        BDDMockito.when(vehicleRepositoryMock.listVehiclesInStock())
                .thenReturn(List.of(VehicleCreator.createValidForSaleVehicle()));

        BDDMockito.when(vehicleRepositoryMock.listSoldVehicles())
                .thenReturn(List.of(VehicleCreator.createValidSoldVehicle()));

        BDDMockito.when(vehicleRepositoryMock.save(VehicleCreator.createVehicleForSaleToBeSaved()))
                .thenReturn(VehicleCreator.createValidForSaleVehicle());

        BDDMockito.when(vehicleRepositoryMock.save(VehicleCreator.createVehicleSoldToBeSaved()))
                .thenReturn(VehicleCreator.createValidSoldVehicle());

        BDDMockito.doNothing().when(vehicleRepositoryMock).delete(any(Vehicle.class));

        BDDMockito.when(vehicleRepositoryMock.save(VehicleCreator.createValidForSaleVehicle()))
                .thenReturn(VehicleCreator.createValidUpdateVehicleForSale());

        BDDMockito.when(vehicleRepositoryMock.save(VehicleCreator.createValidSoldVehicle()))
                .thenReturn(VehicleCreator.createValidUpdateVehicleSold());

        BDDMockito.when(vehicleUtilMock.findVehicleOrThrowNotFound(anyLong(), any(VehicleRepository.class)))
                .thenReturn(VehicleCreator.createValidForSaleVehicle());
    }

    @Test
    @DisplayName("ListAll returns a iterable list of vehicles when successful")
    @SuppressWarnings("unchecked")
    void listAll_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        String expectedModel = VehicleCreator.createValidForSaleVehicle().getModel();

        Iterable<Vehicle> vehicleIterable = vehicleService.listAllVehicles(PageRequest.of(1, 1).getSort());

        List<Vehicle> vehicleList = IteratorUtils.toList(vehicleIterable.iterator());

        Assertions.assertThat(vehicleIterable).isNotEmpty();

        Assertions.assertThat(vehicleList.get(0).getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("findVehicleById return an vehicle when successful")
    void findVehicleById_ReturnAnVehicleById_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidForSaleVehicle().getId();

        Vehicle vehicle = vehicleService.findById(1L);

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("listVehiclesByModel returns a iterable list of vehicles when successful")
    void listVehiclesByModel_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        String expectedModel = VehicleCreator.createValidForSaleVehicle().getModel();

        List<Vehicle> vehicleList = vehicleService.listVehiclesByModel("Golf");

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("listVehiclesInStock returns a iterable list of vehicles in stock when successful")
    void listVehiclesInStock_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        List<Vehicle> vehicleList = vehicleService.listVehiclesInStock();

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).isSold()).isFalse();
    }

    @Test
    @DisplayName("listSoldVehicles returns a iterable list of vehicles in stock when successful")
    void listSoldVehicles_ReturnListOfVehiclesInsidePageObject_WhenSuccessful() {
        List<Vehicle> vehicleList = vehicleService.listSoldVehicles();

        Assertions.assertThat(vehicleList).isNotNull();

        Assertions.assertThat(vehicleList.isEmpty()).isFalse();

        Assertions.assertThat(vehicleList.get(0).isSold()).isTrue();
    }

    @Test
    @DisplayName("Save create an vehicle for sale when successful")
    void save_CreateVehicleForSale_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidForSaleVehicle().getId();

        Vehicle vehicleToBeSaved = VehicleCreator.createVehicleForSaleToBeSaved();

        Vehicle vehicle = vehicleService.saveVehicle(vehicleToBeSaved);

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save create an vehicle sold when successful")
    void save_CreateVehicleSold_WhenSuccessful() {
        Long expectedId = VehicleCreator.createValidSoldVehicle().getId();

        Vehicle vehicleToBeSaved = VehicleCreator.createVehicleSoldToBeSaved();

        Vehicle vehicle = vehicleService.saveVehicle(vehicleToBeSaved);

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete removes the vehicle when successful")
    void delete_RemoveVehicle_WhenSuccessful() {
        Assertions.assertThatCode(() -> vehicleService.deleteVehicleById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete throws ResourceNotFoundException when the vehicle does not exist")
    void delete_ThrowsResourceNotFoundException_WhenVehicleDoesNotExist() {
        BDDMockito.when(vehicleUtilMock.findVehicleOrThrowNotFound(anyLong(), any(VehicleRepository.class)))
                .thenThrow(new ResourceNotFoundException("Vehicle not found"));

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> vehicleService.deleteVehicleById(1L));
    }

    @Test
    @DisplayName("Save updating update vehicle for sale when successful")
    void save_SaveUpdatedVehicleForSale_WhenSuccessful() {
        Vehicle validUpdateVehicleForSale = VehicleCreator.createValidUpdateVehicleForSale();

        String expectedModel = validUpdateVehicleForSale.getModel();

        Vehicle vehicle = vehicleService.saveVehicle(VehicleCreator.createValidForSaleVehicle());

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getModel()).isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("Save updating update vehicle sold when successful")
    void save_SaveUpdatedVehicleSold_WhenSuccessful() {
        Vehicle validUpdateVehicleSold = VehicleCreator.createValidUpdateVehicleSold();

        String expectedModel = validUpdateVehicleSold.getModel();

        Vehicle vehicle = vehicleService.saveVehicle(VehicleCreator.createValidSoldVehicle());

        Assertions.assertThat(vehicle).isNotNull();

        Assertions.assertThat(vehicle.getId()).isNotNull();

        Assertions.assertThat(vehicle.getModel()).isEqualTo(expectedModel);
    }
}
