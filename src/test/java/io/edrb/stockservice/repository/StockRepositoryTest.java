package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.util.DateTimeRange;
import io.edrb.stockservice.utils.RangeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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

    @Test void shouldFindByProductId() {
        Stock stockSaved = entityManager.persist(vegetableStock());
        entityManager.flush();

        Optional<Stock> stock = stockRepository.findByProductId("vegetable");

        Assertions.assertAll(
                () -> Assertions.assertTrue(stock.isPresent()),
                () -> Assertions.assertEquals(stockSaved.getId(), stock.get().getId())
        );
    }

    @Test void shouldReturnProductStocksBetweenRange() {
        Stream.of(
                dynamicStock(clock.minusDays(1), 2),
                dynamicStock(clock.minusHours(2), 10),
                dynamicStock(clock.minusHours(1), 11),
                dynamicStock(clock.minusMinutes(35), 12)
        ).forEach(entityManager::persist);

        entityManager.flush();


        DateTimeRange dateTimeRange = RangeGenerator.generateForToday(clock);

        Optional<List<Stock>> stocks =
                stockRepository.findByTimestampBetweenOrderByQuantityDesc(
                        dateTimeRange.getFrom(), dateTimeRange.getEnd());

        Assertions.assertAll(
                () -> Assertions.assertTrue(stocks.isPresent()),
                () -> Assertions.assertEquals(3, stocks.get().size())
        );
    }


    private Stock vegetableStock() {
        return Stock.builder()
                .timestamp(clock)
                .quantity(100)
                .productId("vegetable")
                .build();
    }

    private Stock dynamicStock(ZonedDateTime timestamp, Integer quantity) {
        String uuid = UUID.randomUUID().toString();

        return Stock.builder()
                .timestamp(timestamp)
                .quantity(quantity)
                .productId("product-" + uuid)
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
