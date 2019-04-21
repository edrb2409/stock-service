package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.ProductsSoldHistorical;
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
public class ProductsSoldHistoricalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductsSoldHistoricalRepository repository;

    private static ZonedDateTime clock;

    @BeforeAll static void init() {
        clock = ZonedDateTime.now();
    }

    @Test void shouldSaveANewHistoricalProductSold() {
        ProductsSoldHistorical historical = repository.save(ProductsSoldHistorical
                .builder()
                .itemsSold(12)
                .productId("vegetable")
                .timestamp(ZonedDateTime.now())
                .build());

        Optional<ProductsSoldHistorical> stock = repository.findById(historical.getId());

        Assertions.assertTrue(stock.isPresent());
    }

    @Test void shouldReturnHistoricalBetweenRange() {
        Stream.of(
                dynamicHistorical(clock.minusDays(1), 2),
                dynamicHistorical(clock.minusMinutes(15), 10),
                dynamicHistorical(clock.minusMinutes(17), 11),
                dynamicHistorical(clock.minusMinutes(35), 12),
                dynamicHistorical(clock.minusMinutes(19), 7)
        ).forEach(entityManager::persist);

        entityManager.flush();

        DateTimeRange dateTimeRange = RangeGenerator.generateForToday(clock);

        Optional<List<ProductsSoldHistorical>> historical =
                repository.findTop3ByTimestampBetweenOrderByItemsSoldDesc(
                        dateTimeRange.getFrom(), dateTimeRange.getEnd());

        Assertions.assertAll(
                () -> Assertions.assertTrue(historical.isPresent()),
                () -> Assertions.assertEquals(3, historical.get().size())
        );
    }

    private ProductsSoldHistorical dynamicHistorical(ZonedDateTime timestamp, int sold) {
        String uuid = UUID.randomUUID().toString();

        return ProductsSoldHistorical.builder()
                .timestamp(timestamp)
                .productId("product-" + uuid)
                .itemsSold(sold)
                .build();
    }

}
