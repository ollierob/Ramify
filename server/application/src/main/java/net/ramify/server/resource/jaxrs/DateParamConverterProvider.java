package net.ramify.server.resource.jaxrs;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Provider
@Singleton
public class DateParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == LocalDate.class) return (ParamConverter<T>) new LocalDateParamConverter();
        return null;
    }

    static class LocalDateParamConverter implements ParamConverter<LocalDate> {

        @Override
        public LocalDate fromString(final String value) {
            return LocalDate.parse(value);
        }

        @Override
        public String toString(final LocalDate date) {
            return date.toString();
        }

    }

}
