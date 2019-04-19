package io.edrb.stockservice.controller;

import io.edrb.stockservice.controller.mapper.UpdateStockMapper;
import io.edrb.stockservice.model.dto.UpdateStockDTO;
import io.edrb.stockservice.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController("/updateStock")
public class UpdateStockController {

    private final StockService stockService;

    public UpdateStockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity updateStock(@Valid @RequestBody UpdateStockDTO updateStockDTO) {
        log.debug("UpdateStock: {}", updateStockDTO);

        stockService.updateStock(UpdateStockMapper.toStock(updateStockDTO));

        return ResponseEntity.ok().build();
    }

}
