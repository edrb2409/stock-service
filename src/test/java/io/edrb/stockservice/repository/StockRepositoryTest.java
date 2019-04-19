package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class StockRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    private static ZonedDateTime clock;

    @BeforeAll static void init() {
        clock = ZonedDateTime.now();
    }

    @Test void shouldFindAStockBasedOnId() {
        Stock stockSaved = entityManager.persist(vegetableStock());
        entityManager.flush();

        Optional<Stock> stock = stockRepository.findById(stockSaved.getId());

        Assertions.assertTrue(stock.isPresent());
    }

    @Test void shouldNotFindAnythingIfIdDoesNotExists() {
        entityManager.persist(vegetableStock());
        entityManager.flush();

        Optional<Stock> stock = stockRepository.findById("11");

        Assertions.assertFalse(stock.isPresent());
    }

    @Test void shouldSaveANewProductStock() {
        Stock stockSaved = stockRepository.save(vegetableStock());

        Optional<Stock> stock = stockRepository.findById(stockSaved.getId());

        Assertions.assertTrue(stock.isPresent());
    }

    @Test void shouldUpdateExistedProductStock() {
        Stock stockSaved = entityManager.persist(vegetableStock());
        entityManager.flush();

        stockRepository.save(newVegetableStock(stockSaved.getId()));

        Optional<Stock> updatedStock = stockRepository.findById(stockSaved.getId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(updatedStock.isPresent()),
                () -> Assertions.assertEquals(200, updatedStock.get().getQuantity())
        );

    }

    private Stock vegetableStock() {
        return Stock.builder()
                .timestamp(clock)
                .quantity(100)
                .productId("vegetable")
                .build();
    }

    private Stock newVegetableStock(String id) {
        return Stock.builder()
                .id(id)
                .timestamp(clock.plusSeconds(30))
                .quantity(200)
                .productId("vegetable")
                .build();
    }

}
