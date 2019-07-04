package net.ramify.server.resource.jaxrs;

import com.google.protobuf.Message;

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

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF_TYPE;

@Provider
@Singleton
class ProtobufDirectMessageBodyWriter implements MessageBodyWriter<Message> {

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return isProtobufType(mediaType)
                && Message.class.isAssignableFrom(type);
    }

    static boolean isProtobufType(final MediaType type) {
        return APPLICATION_PROTOBUF_TYPE.isCompatible(type);
    }

    @Override
    public void writeTo(final Message message, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        message.writeTo(outputStream);
    }

}
