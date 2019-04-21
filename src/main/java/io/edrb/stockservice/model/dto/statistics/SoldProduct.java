package io.edrb.stockservice.model.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SoldProduct {

    private String productId;

    private Long itemsSold;

}
