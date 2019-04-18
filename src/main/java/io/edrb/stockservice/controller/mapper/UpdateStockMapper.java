package io.edrb.stockservice.controller.mapper;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.UpdateStockDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateStockMapper {

    private UpdateStockMapper() {}

    public static Stock toStock(UpdateStockDTO dto) {
        log.debug("Converting to Stock");

        return Stock.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .timestamp(dto.getTimestamp())
                .build();
    }

    public static UpdateStockDTO toDto(Stock stock) {
        log.debug("Converting to UpdateStockDTO");

        return UpdateStockDTO.builder()
                .id(stock.getId())
                .productId(stock.getProductId())
                .quantity(stock.getQuantity())
                .timestamp(stock.getTimestamp())
                .build();
    }
}
