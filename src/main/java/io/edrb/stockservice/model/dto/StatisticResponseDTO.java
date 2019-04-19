package io.edrb.stockservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.edrb.stockservice.model.StatisticRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StatisticResponseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private ZonedDateTime requestTimestamp;

    private StatisticRange range;

    private List<ProductStockInformation> topAvailableProducts;

    private List<SoldProduct> topSellingProducts;

}
