package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.ProductNotFoundException;
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
public class QueryStockServiceTest {

    private QueryStockService queryStockService;

    @Mock StockRepository repository;

    @BeforeEach void init() {
        queryStockService = new DefaultQueryStockService(repository);
    }

    @Test void shouldReturnAStockWithTheGivenProductId() {
        when(repository.findByProductId("vegetable")).thenReturn(Optional.of(vegetableStock()));

        Assertions.assertNotNull(queryStockService.getStockByProductId("vegetable"));
    }

    @Test void shouldRaiseAnExceptionWhenProductDoesNotExists() {
        when(repository.findByProductId("anotherVegetable")).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class,
                () -> queryStockService.getStockByProductId("anotherVegetable"));
    }

    private Stock vegetableStock() {
        return Stock.builder()
                .id("1")
                .timestamp(ZonedDateTime.now())
                .quantity(12)
                .productId("vegetable")
                .build();
    }
}
