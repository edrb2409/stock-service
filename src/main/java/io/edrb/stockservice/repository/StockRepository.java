package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findByProductId(String productId);

}
