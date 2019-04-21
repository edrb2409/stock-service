package io.edrb.stockservice.controller;

import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.dto.statistics.StatisticResponseDTO;
import io.edrb.stockservice.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<StatisticResponseDTO> getStatistics(@RequestParam StatisticRange time) {
        log.debug("Requesting statistics for {}", time);

        return ResponseEntity.ok(
                service.getStatisticsFor(time, ZonedDateTime.now()));
    }
}
