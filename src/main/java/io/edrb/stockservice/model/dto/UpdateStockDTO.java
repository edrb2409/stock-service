package io.edrb.stockservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockDTO {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @NotNull
    private ZonedDateTime timestamp;

    @NotBlank
    private String productId;

    @Min(0)
    private Integer quantity;

}
