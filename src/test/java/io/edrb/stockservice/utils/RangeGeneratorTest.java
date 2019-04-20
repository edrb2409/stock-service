package io.edrb.stockservice.utils;

import io.edrb.stockservice.model.util.DateTimeRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class RangeGeneratorTest {

    @Test void shouldGenerateARangeForToday() {
        ZonedDateTime now = Instant.parse("2017-03-03T13:14:28.666Z").atZone(ZoneOffset.UTC);

        DateTimeRange dateTimeRange = RangeGenerator.generateForToday(now);

        Assertions.assertAll(
                () -> Assertions.assertEquals(Instant.parse("2017-03-03T00:00:00.000Z").atZone(ZoneOffset.UTC), dateTimeRange.getFrom()),
                () -> Assertions.assertEquals(now, dateTimeRange.getEnd())
        );
    }

    @Test void shouldGenerateARangeForMonth() {
        ZonedDateTime now = Instant.parse("2017-03-03T13:14:28.666Z").atZone(ZoneOffset.UTC);

        DateTimeRange dateTimeRange = RangeGenerator.generateForMonth(now);

        Assertions.assertAll(
                () -> Assertions.assertEquals(Instant.parse("2017-03-01T00:00:00.000Z").atZone(ZoneOffset.UTC), dateTimeRange.getFrom()),
                () -> Assertions.assertEquals(now, dateTimeRange.getEnd())
        );
    }
}
