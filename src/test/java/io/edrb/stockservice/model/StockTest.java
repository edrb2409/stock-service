package io.edrb.stockservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class StockTest {

    Stock stock;

    static ZonedDateTime clock;

    @BeforeAll static void initAll() {
        clock = ZonedDateTime.now();
    }

    @BeforeEach void init() {
        stock = Stock.builder()
                .timestamp(clock)
                .quantity(10)
                .build();
    }

    @Test void shouldReturnTrueWhenNewStockIsOutdatedOfCurrent() {
        Stock newStock = Stock.builder()
                .timestamp(clock.minusSeconds(10))
                .build();

        Assertions.assertTrue(stock.isOutdated(newStock));
    }

    @Test void shouldReturnFalseWhenNewStockIsNotOutdatedOfCurrent() {
        Stock newStock = Stock.builder()
                .timestamp(clock.plusSeconds(10))
                .build();

        Assertions.assertFalse(stock.isOutdated(newStock));
    }

    @Test void shouldReturnTrueWhenStockWasSold() {
        Stock newStock = Stock.builder()
                .quantity(9)
                .build();

        Assertions.assertTrue(stock.isASold(newStock));
    }

    @Test void shouldReturnFalseWhenStockWasNotASold() {
        Stock newStock = Stock.builder()
                .quantity(11)
                .build();

        Assertions.assertFalse(stock.isASold(newStock));
    }

}
