package net.ramify.server.resource.jaxrs;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Provider
@Singleton
public class DateRangeParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == DateRange.class) return (ParamConverter<T>) new DateRangeParamConverter();
        return null;
    }

    static final class DateRangeParamConverter implements ParamConverter<DateRange> {

        @Override
        public DateRange fromString(final String value) {
            final var slash = value.indexOf('/');
            if (slash > 0) return ClosedDateRange.of(LocalDate.parse(value.substring(0, slash)), LocalDate.parse(value.substring(slash + 1)));
            throw new UnsupportedOperationException(); //TODO
        }

        @SuppressWarnings("OptionalIsPresent")
        @Override
        public String toString(final DateRange value) {
            final var from = value.earliestInclusive().map(ChronoLocalDate::toString);
            final var to = value.latestInclusive().map(ChronoLocalDate::toString);
            if (from.isPresent() && to.isPresent()) return from.get() + "/" + to.get();
            if (from.isPresent()) return from.get();
            if (to.isPresent()) return to.get();
            return "";
        }
    }

}
