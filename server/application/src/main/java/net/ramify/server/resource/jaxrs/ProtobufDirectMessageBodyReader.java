package net.ramify.server.resource.jaxrs;

import com.google.common.collect.Maps;
import com.google.protobuf.Message;
import net.ramify.model.record.proto.RecordProto;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import static net.ramify.server.resource.jaxrs.ProtobufCompatibleMessageBodyWriter.isProtobufType;

@Provider
@Singleton
class ProtobufDirectMessageBodyReader implements MessageBodyReader<Message> {

    private final Map<Class<?>, MessageReader> messageReaders = Maps.newHashMap();

    {
        messageReaders.put(RecordProto.RecordSearch.class, RecordProto.RecordSearch::parseFrom);
    }

    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return messageReaders.containsKey(type) && isProtobufType(mediaType);
    }

    @Override
    public Message readFrom(
            final Class<Message> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, String> httpHeaders,
            final InputStream entityStream) throws IOException, WebApplicationException {
        return messageReaders.get(type).parse(entityStream);
    }

    private interface MessageReader {

        Message parse(InputStream inputStream) throws IOException;

    }

}
