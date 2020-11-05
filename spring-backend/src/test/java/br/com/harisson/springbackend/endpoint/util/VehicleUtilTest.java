package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class VehicleUtilTest {
    @InjectMocks
    private VehicleUtil vehicleUtil;

    @Mock
    private VehicleRepository vehicleRepositoryMock;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(vehicleRepositoryMock.findById(1L))
                .thenReturn(Optional.of(VehicleCreator.createValidForSaleVehicle()));
    }

    @Test
    @DisplayName("Find vehicle throws ResourceNotFoundException when the vehicle does not exist")
    void findVehicle_ThrowsResourceNotFoundException_WhenVehicleDoesNotExist() {
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> vehicleUtil.findVehicleOrThrowNotFound(2L, vehicleRepositoryMock));
    }

    @Test
    @DisplayName("Find vehicle when successful")
    void findVehicle_ReturnsVehicle_WhenSuccessful() {
        Assertions.assertThatCode(() -> vehicleUtil.findVehicleOrThrowNotFound(1L, vehicleRepositoryMock))
                .doesNotThrowAnyException();
    }
}
