package net.ramify.server.resource.jaxrs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.protobuf.Message;
import net.ramify.model.Id;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import org.jboss.resteasy.spi.util.Types;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Function;

import static net.ramify.server.resource.jaxrs.ProtobufMessageBodyWriter.isProtobufType;

@Singleton
@Provider
class ProtobufListMessageBodyWriter implements MessageBodyWriter<Collection<?>> {

    private static final ImmutableMap<Class<?>, Function<Collection, Message>> MESSAGE_CREATORS;

    static {
        final var builder = ImmutableMap.<Class<?>, Function<Collection, Message>>builder();
        builder.put(PlaceId.class, ProtobufListMessageBodyWriter::placeIdList);
        MESSAGE_CREATORS = builder.build();
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return isProtobufType(mediaType)
                && MESSAGE_CREATORS.containsKey(Types.getCollectionBaseType(type, genericType))
                && Collection.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(Collection<?> objects, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        final var function = MESSAGE_CREATORS.get(Types.getCollectionBaseType(type, genericType));
        function.apply(objects).writeTo(outputStream);
    }

    private static PlaceProto.PlaceIdList placeIdList(final Collection<PlaceId> ids) {
        return PlaceProto.PlaceIdList.newBuilder().addAllId(Iterables.transform(ids, Id::value)).build();
    }

}
