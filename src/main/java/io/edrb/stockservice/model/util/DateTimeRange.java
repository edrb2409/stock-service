package io.edrb.stockservice.model.util;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class DateTimeRange {

    private ZonedDateTime from;

    private ZonedDateTime end;

}
