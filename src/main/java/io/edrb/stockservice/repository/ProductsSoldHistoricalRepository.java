package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.ProductsSoldHistorical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsSoldHistoricalRepository extends JpaRepository<ProductsSoldHistorical, String> {

    Optional<List<ProductsSoldHistorical>> findTop3ByTimestampBetweenOrderByItemsSoldDesc(ZonedDateTime timestampStart, ZonedDateTime timestampEnd);

}
