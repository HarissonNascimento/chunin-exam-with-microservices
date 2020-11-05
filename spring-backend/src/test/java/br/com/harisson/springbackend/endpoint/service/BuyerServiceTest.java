package br.com.harisson.springbackend.endpoint.service;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.BuyerRepository;
import br.com.harisson.springbackend.endpoint.exception.ResourceNotFoundException;
import br.com.harisson.springbackend.endpoint.util.BuyerCreator;
import br.com.harisson.springbackend.endpoint.util.BuyerUtil;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class BuyerServiceTest {
    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private BuyerRepository buyerRepositoryMock;

    @Mock
    private BuyerUtil buyerUtilMock;

    @BeforeEach
    public void setUp() {
        Iterable<Buyer> buyerPage = new PageImpl<>(List.of(BuyerCreator.createValidNonContactedBuyer()));
        BDDMockito.when(buyerRepositoryMock.findAll(any(Sort.class)))
                .thenReturn(buyerPage);

        BDDMockito.when(buyerRepositoryMock.findBuyerById(anyLong()))
                .thenReturn(BuyerCreator.createValidNonContactedBuyer());

        BDDMockito.when(buyerRepositoryMock.findBuyersByVehicleId(anyLong()))
                .thenReturn(List.of(BuyerCreator.createValidNonContactedBuyer()));

        BDDMockito.when(buyerRepositoryMock.listNonContactedBuyers())
                .thenReturn(List.of(BuyerCreator.createValidNonContactedBuyer()));

        BDDMockito.when(buyerRepositoryMock.listContactedBuyers())
                .thenReturn(List.of(BuyerCreator.createValidContactedBuyer()));

        BDDMockito.when(buyerRepositoryMock.save(BuyerCreator.createNonContactedBuyerToBeSaved()))
                .thenReturn(BuyerCreator.createValidNonContactedBuyer());

        BDDMockito.when(buyerRepositoryMock.save(BuyerCreator.createContactedBuyerToBeSaved()))
                .thenReturn(BuyerCreator.createValidContactedBuyer());

        BDDMockito.doNothing().when(buyerRepositoryMock).delete(any(Buyer.class));

        BDDMockito.when(buyerRepositoryMock.save(BuyerCreator.createValidNonContactedBuyer()))
                .thenReturn(BuyerCreator.createValidUpdateNonContactedBuyer());

        BDDMockito.when(buyerRepositoryMock.save(BuyerCreator.createValidContactedBuyer()))
                .thenReturn(BuyerCreator.createValidUpdateContactedBuyer());

        BDDMockito.when(buyerUtilMock.findBuyerOrThrowNotFound(anyLong(), any(BuyerRepository.class)))
                .thenReturn(BuyerCreator.createValidNonContactedBuyer());
    }

    @Test
    @DisplayName("ListAll returns a iterable list of buyers when successful")
    @SuppressWarnings("unchecked")
    void listAll_ReturnListOfBuyersInsidePageObject_WhenSuccessful() {
        String expectedName = BuyerCreator.createValidNonContactedBuyer().getName();

        Iterable<Buyer> buyerIterable = buyerService.listAllBuyers(PageRequest.of(1, 1).getSort());

        List<Buyer> buyerList = IteratorUtils.toList(buyerIterable.iterator());

        Assertions.assertThat(buyerIterable).isNotEmpty();

        Assertions.assertThat(buyerList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findBuyerById return an buyer when successful")
    void findBuyerById_ReturnAnBuyerById_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidNonContactedBuyer().getId();

        Buyer buyer = buyerService.findById(1L);

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findBuyersByVehicleId return a buyer list by vehicle id when successful")
    void findBuyersByVehicleId_ReturnListOfBuyersByVehicleId_WhenSuccessful() {
        Vehicle expectedVehicle = BuyerCreator.createValidNonContactedBuyer().getVehicle();

        List<Buyer> buyerList = buyerService.findBuyersByVehicleId(expectedVehicle.getId());

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).getVehicle()).isEqualTo(expectedVehicle);
    }

    @Test
    @DisplayName("listNonContactedBuyers return a non contacted buyer list when successful")
    void listNonContactedBuyers_ReturnNonContactedBuyerList_WhenSuccessful() {
        List<Buyer> buyerList = buyerService.listNonContactedBuyers();

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).isContacted()).isFalse();
    }

    @Test
    @DisplayName("listContactedBuyers return a contacted buyer list when successful")
    void listNonContactedBuyers_ReturnContactedBuyerList_WhenSuccessful() {
        List<Buyer> buyerList = buyerService.listContactedBuyers();

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).isContacted()).isTrue();
    }

    @Test
    @DisplayName("Save create an non contacted buyer when successful")
    void save_CreateNonContactedBuyer_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidNonContactedBuyer().getId();

        Buyer buyerToBeSaved = BuyerCreator.createNonContactedBuyerToBeSaved();

        Buyer buyer = buyerService.saveBuyer(buyerToBeSaved);

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save create an contacted buyer when successful")
    void save_CreateContactedBuyer_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidContactedBuyer().getId();

        Buyer buyerToBeSaved = BuyerCreator.createContactedBuyerToBeSaved();

        Buyer buyer = buyerService.saveBuyer(buyerToBeSaved);

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete removes the buyer when successful")
    void delete_RemovesBuyer_WhenSuccessful() {
        Assertions.assertThatCode(() -> buyerService.deleteBuyerById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete throws ResourceNotFoundException when the buyer does not exist")
    void delete_ThrowsResourceNotFoundException_WhenBuyerDoesNotExist() {
        BDDMockito.when(buyerUtilMock.findBuyerOrThrowNotFound(anyLong(), any(BuyerRepository.class)))
                .thenThrow(new ResourceNotFoundException("Buyer not found"));

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> buyerService.deleteBuyerById(1L));
    }

    @Test
    @DisplayName("Save updating update non contacted buyer when successful")
    void save_SaveUpdatedNonContactedBuyer_WhenSuccessful() {
        Buyer validNonContactedBuyer = BuyerCreator.createValidUpdateNonContactedBuyer();

        String expectedName = validNonContactedBuyer.getName();

        Buyer buyer = buyerService.saveBuyer(BuyerCreator.createValidNonContactedBuyer());

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Save updating update contacted buyer when successful")
    void save_SaveUpdatedContactedBuyer_WhenSuccessful() {
        Buyer validContactedBuyer = BuyerCreator.createValidUpdateContactedBuyer();

        String expectedName = validContactedBuyer.getName();

        Buyer buyer = buyerService.saveBuyer(BuyerCreator.createValidContactedBuyer());

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Updating non contacted buyer when successful")
    void update_UpdatingNonContactedBuyer_WhenSuccessful() {
        Buyer validNonContactedBuyer = BuyerCreator.createValidUpdateNonContactedBuyer();

        String expectedName = validNonContactedBuyer.getName();

        buyerService.updateBuyer(validNonContactedBuyer);

        Buyer updateBuyer = buyerService.findById(validNonContactedBuyer.getId());

        Assertions.assertThat(updateBuyer).isNotNull();

        Assertions.assertThat(updateBuyer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Updating contacted buyer when successful")
    void update_UpdatingContactedBuyer_WhenSuccessful() {
        Buyer validContactedBuyer = BuyerCreator.createValidUpdateContactedBuyer();

        String expectedName = validContactedBuyer.getName();

        buyerService.updateBuyer(validContactedBuyer);

        Buyer updateBuyer = buyerService.findById(validContactedBuyer.getId());

        Assertions.assertThat(updateBuyer).isNotNull();

        Assertions.assertThat(updateBuyer.getName()).isEqualTo(expectedName);
    }
}
