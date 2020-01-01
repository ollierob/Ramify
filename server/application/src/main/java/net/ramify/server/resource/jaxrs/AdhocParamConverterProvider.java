package net.ramify.server.resource.jaxrs;

import com.google.common.collect.ImmutableMap;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSetType;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

@Provider
@Singleton
public class AdhocParamConverterProvider implements ParamConverterProvider {

    private static final Map<Class<?>, ParamConverter<?>> converters = ImmutableMap.of(
            RecordSetType.class, new AdhocConverter<>(RecordSetTypes::valueOf, RecordSetType::name)
    );

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        return (ParamConverter<T>) converters.get(rawType);
    }

    private static final class AdhocConverter<T> implements ParamConverter<T> {

        private final Function<? super String, ? extends T> toObject;
        private final Function<? super T, String> toString;

        private AdhocConverter(Function<? super String, ? extends T> toObject, Function<? super T, String> toString) {
            this.toObject = toObject;
            this.toString = toString;
        }

        @Override
        public T fromString(final String value) {
            return toObject.apply(value);
        }

        @Override
        public String toString(final T value) {
            return toString.apply(value);
        }

    }

}
