package io.edrb.stockservice.controller.mapper;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.StockInformation;
import io.edrb.stockservice.model.dto.StockResponseDTO;

import java.time.ZonedDateTime;

public final class StockResponseMapper {

    private StockResponseMapper() {}

    public static StockResponseDTO toStockResponseDTO(Stock stock, ZonedDateTime requestTimestamp) {
        return StockResponseDTO.builder()
                .productId(stock.getProductId())
                .requestTimestamp(requestTimestamp)
                .stock(StockInformation.builder()
                        .id(stock.getId())
                        .quantity(stock.getQuantity())
                        .timestamp(stock.getTimestamp())
                        .build())
                .build();
    }

}
