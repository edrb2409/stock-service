package io.edrb.stockservice.service;

import io.edrb.stockservice.exception.NoUniqueProductId;
import io.edrb.stockservice.exception.OutdatedStockException;
import io.edrb.stockservice.model.ProductsSoldHistorical;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.repository.ProductsSoldHistoricalRepository;
import io.edrb.stockservice.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@Slf4j
public class DefaultUpdateStockService implements UpdateStockService {

    private final StockRepository repository;

    private final ProductsSoldHistoricalRepository historicalRepository;

    public DefaultUpdateStockService(StockRepository repository,
                                     ProductsSoldHistoricalRepository historicalRepository) {
        this.repository = repository;
        this.historicalRepository = historicalRepository;
    }

    @Override
    public Stock updateStock(Stock newStock) {
        if(StringUtils.isEmpty(newStock.getId())) {
            return save(newStock);
        }

        Optional<Stock> stockFound = repository.findById(newStock.getId());

        stockFound.filter(it -> !it.isOutdated(newStock))
                .orElseThrow(OutdatedStockException::new);

        stockFound.filter(it -> it.isASold(newStock))
                .ifPresent(it -> historicalRepository.save(
                        ProductsSoldHistorical.builder()
                                .itemsSold(it.getQuantity() - newStock.getQuantity())
                                .productId(it.getProductId())
                                .timestamp(newStock.getTimestamp())
                                .build()
                ));

        return save(newStock);
    }

    private Stock save(Stock newStock) {
        try {
            return repository.save(newStock);
        } catch (DataIntegrityViolationException ex) {
            log.error("Exception: {}", ex.getMessage(), ex);
            throw new NoUniqueProductId();
        }
    }
}
