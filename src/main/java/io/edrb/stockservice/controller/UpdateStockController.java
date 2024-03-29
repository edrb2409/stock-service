package io.edrb.stockservice.controller;

import io.edrb.stockservice.controller.mapper.UpdateStockMapper;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.stock.UpdateStockDTO;
import io.edrb.stockservice.service.UpdateStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/updateStock")
public class UpdateStockController {

    private final UpdateStockService updateStockService;

    public UpdateStockController(UpdateStockService updateStockService) {
        this.updateStockService = updateStockService;
    }

    @PostMapping
    public ResponseEntity updateStock(@Valid @RequestBody UpdateStockDTO updateStockDTO) {
        log.debug("UpdateStock: {}", updateStockDTO);

        Stock stock = updateStockService.updateStock(UpdateStockMapper.toStock(updateStockDTO));

        return ResponseEntity.ok().header("id", stock.getId()).build();
    }

}
