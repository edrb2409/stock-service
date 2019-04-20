package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.ProductNotFoundException;
import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.util.DateTimeRange;
import io.edrb.stockservice.repository.StockRepository;
import io.edrb.stockservice.utils.RangeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DefaultQueryStockService implements QueryStockService {

    private final StockRepository repository;

    public DefaultQueryStockService(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public Stock getStockByProductId(String productId) {
        return repository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Stock> getTopAvailableProducts(StatisticRange range, ZonedDateTime requestTimestamp) {
        DateTimeRange dateTimeRange = range.equals(StatisticRange.today) ?
                RangeGenerator.generateForToday(requestTimestamp) : RangeGenerator.generateForMonth(requestTimestamp);

        return repository.findTop3ByTimestampBetweenOrderByQuantityDesc(dateTimeRange.getFrom(), dateTimeRange.getEnd())
                .orElse(new ArrayList<>());
    }
}
