package io.edrb.stockservice.service;

import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.util.DateTimeRange;
import io.edrb.stockservice.repository.ProductsSoldHistoricalRepository;
import io.edrb.stockservice.utils.RangeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryHistoricalServiceTest {

    private QueryHistoricalService service;

    @Mock
    ProductsSoldHistoricalRepository repository;

    @BeforeEach void init() {
        service = new DefaultQueryHistoricalService(repository);
    }

    @Test void shouldReturnTopSellingProducts() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeRange range = RangeGenerator.generateForToday(now);

        when(repository.findTop3ByTimestampBetweenOrderByItemsSoldDesc(range.getFrom(), range.getEnd()))
                .thenReturn(Optional.empty());

        Assertions.assertTrue(service.getTopSellingProducts(StatisticRange.today, now).isEmpty());
    }

}
