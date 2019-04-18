package io.edrb.stockservice.controller.mapper;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.UpdateStockDTO;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStockMapperTest {

    @Test void shouldMapUpdateStockDtoToStock() {
        ZonedDateTime now = ZonedDateTime.now();

        UpdateStockDTO stockDTO = UpdateStockDTO.builder()
                .productId("vegetable")
                .quantity(100)
                .timestamp(now)
                .id("1")
                .build();

        Stock stockMapped = UpdateStockMapper.toStock(stockDTO);

        assertAll(
                () -> assertEquals("1", stockMapped.getId()),
                () -> assertEquals("vegetable", stockMapped.getProductId()),
                () -> assertEquals(100, stockMapped.getQuantity()),
                () -> assertEquals(now, stockMapped.getTimestamp())
        );
    }

    @Test void shouldMapStockToUpdateStockDto() {
        ZonedDateTime now = ZonedDateTime.now();

        Stock stock = Stock.builder()
                .productId("vegetable")
                .quantity(100)
                .id("1")
                .timestamp(now)
                .build();

        UpdateStockDTO stockDTO = UpdateStockMapper.toDto(stock);

        assertAll(
                () -> assertEquals("1", stockDTO.getId()),
                () -> assertEquals("vegetable", stockDTO.getProductId()),
                () -> assertEquals(100, stockDTO.getQuantity()),
                () -> assertEquals(now, stockDTO.getTimestamp())
        );
    }



}
