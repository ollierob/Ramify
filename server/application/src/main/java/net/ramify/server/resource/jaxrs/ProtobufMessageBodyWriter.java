package net.ramify.server.resource.jaxrs;

import net.ramify.data.proto.WritesProto;

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
class ProtobufMessageBodyWriter implements MessageBodyWriter<WritesProto> {

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return isProtobufType(mediaType)
                && WritesProto.class.isAssignableFrom(aClass);
    }

    static boolean isProtobufType(final MediaType type) {
        return APPLICATION_PROTOBUF_TYPE.isCompatible(type);
    }

    @Override
    public void writeTo(WritesProto writesProto, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        writesProto.writeTo(outputStream);
    }

}
