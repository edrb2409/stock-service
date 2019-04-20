package io.edrb.stockservice.service;

import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.Stock;

import java.time.ZonedDateTime;
import java.util.List;

public interface QueryStockService {

    Stock getStockByProductId(String productId);

    List<Stock> getTopAvailableProducts(StatisticRange range, ZonedDateTime requestTimestamp);

}
