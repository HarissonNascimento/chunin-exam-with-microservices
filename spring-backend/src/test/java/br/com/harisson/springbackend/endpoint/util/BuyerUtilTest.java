package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.repository.BuyerRepository;
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
class BuyerUtilTest {
    @InjectMocks
    private BuyerUtil buyerUtil;

    @Mock
    private BuyerRepository buyerRepositoryMock;

    @BeforeEach
    public void setUp() {
        BDDMockito.when(buyerRepositoryMock.findById(1L))
                .thenReturn(Optional.of(BuyerCreator.createValidNonContactedBuyer()));
    }

    @Test
    @DisplayName("Find buyer throws ResourceNotFoundException when the buyer does not exist")
    void findBuyer_ThrowsResourceNotFoundException_WhenBuyerDoesNotExist() {
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> buyerUtil.findBuyerOrThrowNotFound(2L, buyerRepositoryMock));
    }

    @Test
    @DisplayName("Find buyer when successful")
    void findBuyer_ReturnsBuyer_WhenSuccessful() {
        Assertions.assertThatCode(() -> buyerUtil.findBuyerOrThrowNotFound(1L, buyerRepositoryMock))
                .doesNotThrowAnyException();
    }
}
