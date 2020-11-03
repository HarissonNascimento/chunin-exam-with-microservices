package br.com.harisson.springbackend.endpoint.repository;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.BuyerRepository;
import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.util.BuyerCreator;
import br.com.harisson.springbackend.endpoint.util.VehicleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Buyer Repository Tests")
class BuyerRepositoryTest {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @DisplayName("Save create buyer when successful")
    void save_PersistBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createNonContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        Assertions.assertThat(savedBuyer.getId()).isNotNull();
        Assertions.assertThat(savedBuyer.getVehicle()).isNotNull();
        Assertions.assertThat(savedBuyer.getVehicle()).isEqualTo(buyer.getVehicle());
        Assertions.assertThat(savedBuyer.getName()).isNotNull();
        Assertions.assertThat(savedBuyer.getName()).isEqualTo(buyer.getName());
        Assertions.assertThat(savedBuyer.getNumberPhone()).isNotNull();
        Assertions.assertThat(savedBuyer.getNumberPhone()).isEqualTo(buyer.getNumberPhone());
        Assertions.assertThat(savedBuyer.isContacted()).isEqualTo(buyer.isContacted());
    }

    @Test
    @DisplayName("Save update buyer when successful")
    void save_UpdateBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createNonContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        savedBuyer.setName("Test buyer update");
        savedBuyer.setVehicle(VehicleCreator.createValidForSaleVehicle());
        savedBuyer.setNumberPhone("987654321");
        savedBuyer.setContacted(true);

        Buyer updateBuyer = this.buyerRepository.save(savedBuyer);

        Assertions.assertThat(savedBuyer.getId()).isNotNull();
        Assertions.assertThat(savedBuyer.getVehicle()).isNotNull();
        Assertions.assertThat(savedBuyer.getVehicle()).isEqualTo(updateBuyer.getVehicle());
        Assertions.assertThat(savedBuyer.getName()).isNotNull();
        Assertions.assertThat(savedBuyer.getName()).isEqualTo(updateBuyer.getName());
        Assertions.assertThat(savedBuyer.getNumberPhone()).isNotNull();
        Assertions.assertThat(savedBuyer.getNumberPhone()).isEqualTo(updateBuyer.getNumberPhone());
        Assertions.assertThat(savedBuyer.isContacted()).isEqualTo(buyer.isContacted());
    }

    @Test
    @DisplayName("Delete remove buyer when successful")
    void delete_RemoveBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        this.buyerRepository.delete(buyer);

        Buyer buyerById = this.buyerRepository.findBuyerById(savedBuyer.getId());

        Assertions.assertThat(buyerById).isNull();
    }

    @Test
    @DisplayName("Find by vehicle id returns buyer list when successful")
    void findByVehicleId_ReturnListBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createNonContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        Long id = savedBuyer.getVehicle().getId();

        List<Buyer> buyerList = this.buyerRepository.findBuyersByVehicleId(id);

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList).contains(savedBuyer);
    }

    @Test
    @DisplayName("Find by vehicle id returns empty list when no buyer is found")
    void findByVehicleId_ReturnEmptyList_WhenBuyerNotFound(){
        Long id = 10L; //Fake id

        List<Buyer> buyerList = this.buyerRepository.findBuyersByVehicleId(id);

        Assertions.assertThat(buyerList.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("List buyers non contacted returns buyer list when successful")
    void listNonContactedBuyers_ReturnListBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createNonContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        List<Buyer> buyerList = this.buyerRepository.listNonContactedBuyers();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList).contains(savedBuyer);

        Assertions.assertThat(savedBuyer.isContacted()).isFalse();
    }

    @Test
    @DisplayName("List buyers non contacted returns empty list when no buyer is found")
    void listNonContactedBuyers_ReturnEmptyList_WhenBuyerNotFound(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        List<Buyer> buyerList = this.buyerRepository.listNonContactedBuyers();

        Assertions.assertThat(savedBuyer.isContacted()).isTrue();

        Assertions.assertThat(buyerList.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("List buyers contacted returns buyer list when successful")
    void listContactedBuyers_ReturnListBuyer_WhenSuccessful(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        List<Buyer> buyerList = this.buyerRepository.listContactedBuyers();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList).contains(savedBuyer);

        Assertions.assertThat(savedBuyer.isContacted()).isTrue();
    }

    @Test
    @DisplayName("List buyers contacted returns empty list when no buyer is found")
    void listContactedBuyers_ReturnEmptyList_WhenBuyerNotFound(){
        Vehicle vehicle = this.vehicleRepository.save(VehicleCreator.createVehicleForSaleToBeSaved());

        Buyer buyer = BuyerCreator.createNonContactedBuyerToBeSaved();

        buyer.setVehicle(vehicle);

        Buyer savedBuyer = this.buyerRepository.save(buyer);

        List<Buyer> buyerList = this.buyerRepository.listContactedBuyers();

        Assertions.assertThat(savedBuyer.isContacted()).isFalse();

        Assertions.assertThat(buyerList.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Save throw ContraintViolationException when values is empty")
    void save_ThrowContraintViolationException_WhenValuesIsEmpty(){
        Buyer buyer = new Buyer();

        Assertions.assertThatThrownBy(() -> buyerRepository.save(buyer))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
