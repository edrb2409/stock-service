package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.ProductNotFoundException;
import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.util.DateTimeRange;
import io.edrb.stockservice.repository.StockRepository;
import io.edrb.stockservice.utils.RangeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Test void shouldReturnTopAvailableProducts() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeRange range = RangeGenerator.generateForToday(now);

        when(repository.findTop3ByTimestampBetweenOrderByQuantityDesc(range.getFrom(), range.getEnd()))
                .thenReturn(Optional.empty());

        List<Stock> topStocks = queryStockService.getTopAvailableProducts(StatisticRange.today, now);

        Assertions.assertTrue(topStocks.isEmpty());
    }

    private Stock vegetableStock() {
        return dynamicStock(ZonedDateTime.now(), 12);
    }

    private Stock dynamicStock(ZonedDateTime timestamp, Integer quantity) {
        String uuid = UUID.randomUUID().toString();

        return Stock.builder()
                .timestamp(timestamp)
                .quantity(quantity)
                .productId("product-" + uuid)
                .build();
    }
}
