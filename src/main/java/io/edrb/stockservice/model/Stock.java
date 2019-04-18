package io.edrb.stockservice.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder
@Entity(name = "stocks")
public class Stock {

    @Id
    private String id;

    @NotNull
    private ZonedDateTime timestamp;

    @NotNull
    private String productId;

    @NotNull
    @Min(0)
    private Integer quantity;


}
