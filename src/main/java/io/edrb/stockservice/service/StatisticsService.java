package io.edrb.stockservice.service;

import io.edrb.stockservice.model.StatisticRange;
import io.edrb.stockservice.model.dto.statistics.StatisticResponseDTO;

import java.time.ZonedDateTime;

public interface StatisticsService {

    StatisticResponseDTO getStatisticsFor(StatisticRange range, ZonedDateTime requestTimestamp);
}
