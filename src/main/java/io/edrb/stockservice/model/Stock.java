package io.edrb.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private ZonedDateTime timestamp;

    @NotNull
    @Column(unique = true)
    private String productId;

    @NotNull
    @Min(0)
    private Integer quantity;

    public Stock() {}

    public boolean isOutdated(Stock newStock) {
        return this.timestamp.compareTo(newStock.timestamp) > 0;
    }

    public boolean isASold(Stock newStock) {
        return this.quantity.compareTo(newStock.quantity) > 0;
    }
}
