package io.edrb.stockservice.service;

import io.edrb.stockservice.model.ProductsSoldHistorical;
import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.statistics.ProductStockInformation;
import io.edrb.stockservice.model.dto.statistics.SoldProduct;
import io.edrb.stockservice.model.dto.statistics.StatisticResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultStatisticsService implements StatisticsService {

    private final QueryHistoricalService queryHistoricalService;

    private final QueryStockService queryStockService;

    public DefaultStatisticsService(QueryHistoricalService queryHistoricalService,
                                    QueryStockService queryStockService) {
        this.queryHistoricalService = queryHistoricalService;
        this.queryStockService = queryStockService;
    }

    @Override
    public StatisticResponseDTO getStatisticsFor(StatisticRange range, ZonedDateTime requestTimestamp) {
        List<Stock> topStocks = queryStockService.getTopAvailableProducts(range, requestTimestamp);
        List<ProductsSoldHistorical> topSold = queryHistoricalService.getTopSellingProducts(range, requestTimestamp);

        return StatisticResponseDTO.builder()
                .range(range)
                .requestTimestamp(requestTimestamp)
                .topAvailableProducts(mapStock(topStocks))
                .topSellingProducts(mapSoldProducts(topSold))
                .build();
    }

    private List<ProductStockInformation> mapStock(List<Stock> topStocks) {
        return topStocks.stream()
                .map(it -> ProductStockInformation.builder()
                        .id(it.getId())
                        .timestamp(it.getTimestamp())
                        .productId(it.getProductId())
                        .quantity(it.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    private List<SoldProduct> mapSoldProducts(List<ProductsSoldHistorical> topSold) {
        return topSold.stream()
                .collect(Collectors.groupingBy(ProductsSoldHistorical::getProductId, Collectors.summingInt(ProductsSoldHistorical::getItemsSold)))
                .entrySet().stream()
                    .map(it -> SoldProduct.builder()
                            .productId(it.getKey())
                            .itemsSold(it.getValue())
                            .build())
                    .collect(Collectors.toList());
    }

}
