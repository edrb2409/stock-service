package io.edrb.stockservice.controller;

import io.edrb.stockservice.controller.mapper.StockResponseMapper;
import io.edrb.stockservice.model.dto.stock.StockResponseDTO;
import io.edrb.stockservice.service.QueryStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController("/stock")
public class StockController {

    private final QueryStockService queryStockService;

    public StockController(QueryStockService queryStockService) {
        this.queryStockService = queryStockService;
    }

    @GetMapping
    public ResponseEntity<StockResponseDTO> getStockByProductId(@RequestParam String productId) {
        log.debug("Requesting stock for: {}", productId);

        return ResponseEntity.ok(
                StockResponseMapper.toStockResponseDTO(
                        queryStockService.getStockByProductId(productId), ZonedDateTime.now()));
    }
}
