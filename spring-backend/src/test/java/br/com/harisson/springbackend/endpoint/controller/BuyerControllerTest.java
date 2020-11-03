package br.com.harisson.springbackend.endpoint.controller;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.springbackend.endpoint.service.BuyerService;
import br.com.harisson.springbackend.endpoint.util.BuyerCreator;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class BuyerControllerTest {
    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private BuyerService buyerServiceMock;

    @BeforeEach
    public void setUp() {
        Iterable<Buyer> buyerPage = new PageImpl<>(List.of(BuyerCreator.createValidNonContactedBuyer()));
        BDDMockito.when(buyerServiceMock.listAllBuyers(any(Sort.class)))
                .thenReturn(buyerPage);

        BDDMockito.when(buyerServiceMock.findById(anyLong()))
                .thenReturn(BuyerCreator.createValidNonContactedBuyer());

        BDDMockito.when(buyerServiceMock.findBuyersByVehicleId(anyLong()))
                .thenReturn(List.of(BuyerCreator.createValidNonContactedBuyer()));

        BDDMockito.when(buyerServiceMock.listNonContactedBuyers())
                .thenReturn(List.of(BuyerCreator.createValidNonContactedBuyer()));

        BDDMockito.when(buyerServiceMock.listContactedBuyers())
                .thenReturn(List.of(BuyerCreator.createValidContactedBuyer()));

        BDDMockito.when(buyerServiceMock.saveBuyer(BuyerCreator.createNonContactedBuyerToBeSaved()))
                .thenReturn(BuyerCreator.createValidNonContactedBuyer());

        BDDMockito.when(buyerServiceMock.saveBuyer(BuyerCreator.createContactedBuyerToBeSaved()))
                .thenReturn(BuyerCreator.createValidContactedBuyer());

        BDDMockito.doNothing().when(buyerServiceMock).deleteBuyerById(anyLong());

        BDDMockito.when(buyerServiceMock.saveBuyer(BuyerCreator.createValidNonContactedBuyer()))
                .thenReturn(BuyerCreator.createValidUpdateNonContactedBuyer());

        BDDMockito.when(buyerServiceMock.saveBuyer(BuyerCreator.createValidContactedBuyer()))
                .thenReturn(BuyerCreator.createValidUpdateContactedBuyer());
    }

    @Test
    @DisplayName("ListAll returns a iterable list of buyers when successful")
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    void listAll_ReturnListOfBuyersInsidePageObject_WhenSuccessful() {
        String expectedName = BuyerCreator.createValidNonContactedBuyer().getName();

        Iterable<Buyer> buyerIterable = buyerController.listAllBuyers(PageRequest.of(1, 1).getSort()).getBody();

        List<Buyer> buyerList = IteratorUtils.toList(buyerIterable.iterator());

        Assertions.assertThat(buyerIterable).isNotEmpty();

        Assertions.assertThat(buyerList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findBuyerById return an buyer when successful")
    void findBuyerById_ReturnAnBuyerById_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidNonContactedBuyer().getId();

        Buyer buyer = buyerController.findById(1L).getBody();

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findBuyersByVehicleId return a buyer list by vehicle id when successful")
    void findBuyersByVehicleId_ReturnListOfBuyersByVehicleId_WhenSuccessful() {
        Vehicle expectedVehicle = BuyerCreator.createValidNonContactedBuyer().getVehicle();

        List<Buyer> buyerList = buyerController.findBuyersByVehicleId(expectedVehicle.getId()).getBody();

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).getVehicle()).isEqualTo(expectedVehicle);
    }

    @Test
    @DisplayName("listNonContactedBuyers return a non contacted buyer list when successful")
    void listNonContactedBuyers_ReturnNonContactedBuyerList_WhenSuccessful() {
        List<Buyer> buyerList = buyerController.listNonContactedBuyers().getBody();

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).isContacted()).isFalse();
    }

    @Test
    @DisplayName("listContactedBuyers return a contacted buyer list when successful")
    void listNonContactedBuyers_ReturnContactedBuyerList_WhenSuccessful() {
        List<Buyer> buyerList = buyerController.listContactedBuyers().getBody();

        Assertions.assertThat(buyerList).isNotNull();

        Assertions.assertThat(buyerList.isEmpty()).isFalse();

        Assertions.assertThat(buyerList.get(0).isContacted()).isTrue();
    }

    @Test
    @DisplayName("Save create an non contacted buyer when successful")
    void save_CreateNonContactedBuyer_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidNonContactedBuyer().getId();

        Buyer buyerToBeSaved = BuyerCreator.createNonContactedBuyerToBeSaved();

        Buyer buyer = buyerController.saveBuyer(buyerToBeSaved).getBody();

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save create an contacted buyer when successful")
    void save_CreateContactedBuyer_WhenSuccessful() {
        Long expectedId = BuyerCreator.createValidContactedBuyer().getId();

        Buyer buyerToBeSaved = BuyerCreator.createContactedBuyerToBeSaved();

        Buyer buyer = buyerController.saveBuyer(buyerToBeSaved).getBody();

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete removes the buyer when successful")
    void delete_RemovesBuyer_WhenSuccessful() {
        ResponseEntity<Void> responseEntity = buyerController.deleteBuyerById(1L);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Save updating update non contacted buyer when successful")
    void save_SaveUpdatedNonContactedBuyer_WhenSuccessful() {
        Buyer validNonContactedBuyer = BuyerCreator.createValidUpdateNonContactedBuyer();

        String expectedName = validNonContactedBuyer.getName();

        Buyer buyer = buyerController.saveBuyer(BuyerCreator.createValidNonContactedBuyer()).getBody();

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Save updating update contacted buyer when successful")
    void save_SaveUpdatedContactedBuyer_WhenSuccessful() {
        Buyer validContactedBuyer = BuyerCreator.createValidUpdateContactedBuyer();

        String expectedName = validContactedBuyer.getName();

        Buyer buyer = buyerController.saveBuyer(BuyerCreator.createValidContactedBuyer()).getBody();

        Assertions.assertThat(buyer).isNotNull();

        Assertions.assertThat(buyer.getId()).isNotNull();

        Assertions.assertThat(buyer.getName()).isEqualTo(expectedName);
    }
}
