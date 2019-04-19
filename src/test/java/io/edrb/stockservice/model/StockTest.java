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
                .id("1")
                .timestamp(clock)
                .build();
    }

    @Test void shouldReturnTrueWhenNewStockIsOutdatedOfCurrent() {
        Stock newStock = Stock.builder()
                .id("1")
                .timestamp(clock.minusSeconds(10))
                .build();

        Assertions.assertTrue(stock.isOutdated(newStock));
    }

    @Test void shouldReturnFalseWhenNewStockIsNotOutdatedOfCurrent() {
        Stock newStock = Stock.builder()
                .id("1")
                .timestamp(clock.plusSeconds(10))
                .build();

        Assertions.assertFalse(stock.isOutdated(newStock));
    }

}
