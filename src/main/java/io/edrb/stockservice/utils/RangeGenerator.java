package io.edrb.stockservice.utils;

import io.edrb.stockservice.model.util.DateTimeRange;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class RangeGenerator {

    private RangeGenerator() {}

    public static DateTimeRange generateForToday(ZonedDateTime endRange) {
        return DateTimeRange.builder()
                .from(endRange.truncatedTo(ChronoUnit.DAYS))
                .end(endRange)
                .build();
    }

    public static DateTimeRange generateForMonth(ZonedDateTime endRange) {
        return DateTimeRange.builder()
                .from(firstDayOfMonth(endRange))
                .end(endRange)
                .build();
    }

    private static ZonedDateTime firstDayOfMonth(ZonedDateTime relative) {
        return relative.truncatedTo(ChronoUnit.DAYS)
                .withDayOfMonth(1);
    }
}
