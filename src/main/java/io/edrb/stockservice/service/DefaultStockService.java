package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.OutdatedStockException;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.StockRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DefaultStockService implements StockService {

    private final StockRepository repository;

    public DefaultStockService(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateStock(Stock newStock) {
        if(StringUtils.isEmpty(newStock.getId())) {
            repository.save(newStock);
            return;
        }

        repository.findById(newStock.getId())
                .filter(it -> !it.isOutdated(newStock))
                .orElseThrow(OutdatedStockException::new);

        repository.save(newStock);
    }
}
