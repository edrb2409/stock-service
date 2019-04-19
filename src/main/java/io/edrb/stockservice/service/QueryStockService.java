package io.edrb.stockservice.service;

import io.edrb.stockservice.model.Stock;

public interface QueryStockService {

    Stock getStockByProductId(String productId);

}
