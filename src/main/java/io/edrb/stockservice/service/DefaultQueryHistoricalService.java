package io.edrb.stockservice.service;

import io.edrb.stockservice.model.ProductsSoldHistorical;
import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.util.DateTimeRange;
import io.edrb.stockservice.repository.ProductsSoldHistoricalRepository;
import io.edrb.stockservice.utils.RangeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DefaultQueryHistoricalService implements QueryHistoricalService {

    private final ProductsSoldHistoricalRepository repository;

    public DefaultQueryHistoricalService(ProductsSoldHistoricalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductsSoldHistorical> getTopSellingProducts(StatisticRange range, ZonedDateTime requestTimestamp) {
        DateTimeRange dateTimeRange = range.equals(StatisticRange.today) ?
                RangeGenerator.generateForToday(requestTimestamp) : RangeGenerator.generateForMonth(requestTimestamp);

        return repository.findTop3ByTimestampBetweenOrderByItemsSoldDesc(dateTimeRange.getFrom(), dateTimeRange.getEnd())
                .orElse(new ArrayList<>());
    }
}
