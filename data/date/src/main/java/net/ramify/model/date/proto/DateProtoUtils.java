package net.ramify.model.date.proto;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateProtoUtils {

    @Nonnull
    public static DateProto.Date toProto(final ChronoLocalDate date) {
        if (date instanceof LocalDate) return toProto((LocalDate) date);
        throw new UnsupportedOperationException();
    }

    @Nonnull
    public static DateProto.Date toProto(final LocalDate date) {
        return DateProto.Date.newBuilder()
                .setYear(date.getYear())
                .setMonth(date.getMonthValue())
                .setDay(date.getDayOfMonth())
                .build();
    }

}
