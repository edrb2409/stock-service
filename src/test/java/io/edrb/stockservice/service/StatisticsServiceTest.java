package io.edrb.stockservice.service;

import io.edrb.stockservice.model.ProductsSoldHistorical;
import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.statistics.StatisticResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    private StatisticsService service;

    @Mock QueryStockService stockService;

    @Mock QueryHistoricalService historicalService;

    private ZonedDateTime now;

    @BeforeEach void init() {
        service = new DefaultStatisticsService(historicalService, stockService);

        now = ZonedDateTime.now();
    }

    @Test void shouldReturnEmptyAvailableProductsAndEmptySoldProductsWhenBothDoesNotHaveData() {
        when(stockService.getTopAvailableProducts(StatisticRange.today, now))
                .thenReturn(new ArrayList<>());

        when(historicalService.getTopSellingProducts(StatisticRange.today, now))
                .thenReturn(new ArrayList<>());

        StatisticResponseDTO responseDTO = service.getStatisticsFor(StatisticRange.today, now);

        assertAll(
                () -> assertTrue(responseDTO.getTopAvailableProducts().isEmpty()),
                () -> assertTrue(responseDTO.getTopSellingProducts().isEmpty()),
                () -> assertEquals(StatisticRange.today, responseDTO.getRange()),
                () -> assertEquals(now, responseDTO.getRequestTimestamp())
        );
    }

    @Test void shouldReturnACompleteStatisticResponse() {
        when(stockService.getTopAvailableProducts(StatisticRange.today, now))
                .thenReturn(getStocks());

        when(historicalService.getTopSellingProducts(StatisticRange.today, now))
                .thenReturn(getHistorical());

        StatisticResponseDTO responseDTO = service.getStatisticsFor(StatisticRange.today, now);

        assertAll(
                () -> assertEquals(3, responseDTO.getTopAvailableProducts().size()),
                () -> assertEquals(3, responseDTO.getTopSellingProducts().size()),
                () -> assertEquals(StatisticRange.today, responseDTO.getRange()),
                () -> assertEquals(now, responseDTO.getRequestTimestamp())
        );
    }


    private List<Stock> getStocks() {
        return Stream.of(
                dynamicStock(now.minusMinutes(5), 10),
                dynamicStock(now.minusMinutes(7), 11),
                dynamicStock(now.minusMinutes(9), 12)
        ).collect(toList());
    }

    private List<ProductsSoldHistorical> getHistorical() {
        return Stream.of(
                dynamicHistorical("1", now.minusMinutes(5), 1),
                dynamicHistorical("2", now.minusMinutes(7), 3),
                dynamicHistorical("3", now.minusMinutes(9), 5),
                dynamicHistorical("1", now.minusMinutes(1), 5)
        ).collect(toList());
    }

    private Stock dynamicStock(ZonedDateTime timestamp, Integer quantity) {
        String uuid = UUID.randomUUID().toString();

        return Stock.builder()
                .timestamp(timestamp)
                .quantity(quantity)
                .productId("product-" + uuid)
                .build();
    }

    private ProductsSoldHistorical dynamicHistorical(String id, ZonedDateTime timestamp, int sold) {
        return ProductsSoldHistorical.builder()
                .timestamp(timestamp)
                .productId("product-" + id)
                .itemsSold(sold)
                .build();
    }

}
