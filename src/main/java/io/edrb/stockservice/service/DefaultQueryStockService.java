package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.ProductNotFoundException;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}
