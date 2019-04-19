package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.NoUniqueProductId;
import io.edrb.stockservice.exception.OutdatedStockException;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class DefaultStockService implements StockService {

    private final StockRepository repository;

    public DefaultStockService(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateStock(Stock newStock) {
        if(StringUtils.isEmpty(newStock.getId())) {
            save(newStock);
            return;
        }

        repository.findById(newStock.getId())
                .filter(it -> !it.isOutdated(newStock))
                .orElseThrow(OutdatedStockException::new);

        save(newStock);
    }

    private void save(Stock newStock) {
        try {
            repository.save(newStock);
        } catch (DataIntegrityViolationException ex) {
            log.error("Exception: {}", ex.getMessage(), ex);
            throw new NoUniqueProductId();
        }
    }
}
