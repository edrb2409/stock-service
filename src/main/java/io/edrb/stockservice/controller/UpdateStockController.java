package io.edrb.stockservice.controller;

import io.edrb.stockservice.model.dto.UpdateStockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController("/updateStock")
public class UpdateStockController {

    @PostMapping
    public ResponseEntity updateStock(@Valid @RequestBody UpdateStockDTO updateStockDTO) {
        log.debug("UpdateStock: {}", updateStockDTO);

        return ResponseEntity.ok().build();
    }

}
