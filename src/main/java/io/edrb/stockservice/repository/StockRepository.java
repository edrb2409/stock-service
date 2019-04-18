package io.edrb.stockservice.repository;

import io.edrb.stockservice.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, String> {

}
