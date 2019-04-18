package io.edrb.stockservice.service;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.StockRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultStockService implements StockService {

    private final StockRepository repository;

    public DefaultStockService(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateStock(Stock newStock) {

    }
}
