package io.edrb.stockservice.service;

import io.edrb.stockservice.model.ProductsSoldHistorical;
import io.edrb.stockservice.model.StatisticRange;

import java.time.ZonedDateTime;
import java.util.List;

public interface QueryHistoricalService {

    List<ProductsSoldHistorical> getTopSellingProducts(StatisticRange range, ZonedDateTime requestTimestamp);
}
