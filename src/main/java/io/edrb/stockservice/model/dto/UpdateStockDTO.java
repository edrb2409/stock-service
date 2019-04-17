package io.edrb.stockservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * DTO for incoming stock updates
 *
 * {
 *  "id": “string", // unique string to identify the stock, e.g. "000001"
 *  "timestamp": “dateTime" // datetime in UTC, e.g. "2017-07-16T22:54:01.754Z"
 *  "productId": “string", // unique id to identify the products, e.g. "vegetable-123"
 *  "quantity": “integer" // amount of available stock, e.g. 500
 * }
 *
 */
@Data
@Builder
public class UpdateStockDTO {

    private final String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @NotNull
    private final ZonedDateTime timestamp;

    @NotBlank
    private final String productId;

    @Min(0)
    private final Integer quantity;

}
