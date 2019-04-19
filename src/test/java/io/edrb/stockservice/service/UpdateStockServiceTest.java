package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.OutdatedStockException;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UpdateStockServiceTest {

    UpdateStockService updateStockService;

    ZonedDateTime now;

    @Mock StockRepository repository;

    @BeforeEach void init_() {
        updateStockService = new DefaultUpdateStockService(repository);
        now = ZonedDateTime.now();
    }

    @Test void shouldSaveANewProductStock() {
        Stock vegetableStock = Stock.builder()
                .productId("vegetable")
                .quantity(100)
                .timestamp(now)
                .build();

        when(repository.save(vegetableStock)).thenReturn(vegetableStock(now, 100));

        updateStockService.updateStock(vegetableStock);
    }

    @Test void shouldUpdateAProductStock() {
        Stock newStock = vegetableStock(now.plusSeconds(10), 120);

        when(repository.findById("1")).thenReturn(Optional.of(vegetableStock(now, 100)));
        when(repository.save(newStock)).thenReturn(newStock);

        updateStockService.updateStock(newStock);
    }

    @Test void shouldRaiseAnExceptionWhenTimestampIsBeforeOfStockPreviouslyStored() {
        Stock newStock = vegetableStock(now.minusSeconds(10), 120);

        when(repository.findById("1")).thenReturn(Optional.of(vegetableStock(now, 100)));

        Assertions.assertThrows(OutdatedStockException.class,
                () -> updateStockService.updateStock(newStock));
    }

    private Stock vegetableStock(ZonedDateTime timestamp, Integer quantity) {
        return Stock.builder()
                .id("1")
                .timestamp(timestamp)
                .quantity(quantity)
                .productId("vegetable")
                .build();
    }


}
