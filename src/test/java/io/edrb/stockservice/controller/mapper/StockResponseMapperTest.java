package io.edrb.stockservice.controller.mapper;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.stock.StockResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class StockResponseMapperTest {

    @Test void shouldCreateAStockResponseDTOFromStock() {
        ZonedDateTime clock = ZonedDateTime.now();

        Stock stock = Stock.builder()
                .productId("vegetable")
                .quantity(100)
                .id("1")
                .timestamp(clock.minusMinutes(2))
                .build();

        StockResponseDTO stockResponseDTO = StockResponseMapper.toStockResponseDTO(stock, clock);

        Assertions.assertAll(
                () -> Assertions.assertEquals("vegetable", stockResponseDTO.getProductId()),
                () -> Assertions.assertEquals("1", stockResponseDTO.getStock().getId()),
                () -> Assertions.assertEquals(100, stockResponseDTO.getStock().getQuantity())
        );
    }

}
