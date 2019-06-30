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

@Provider
@Singleton
class ProtobufMessageBodyWriter implements MessageBodyWriter<WritesProto> {

    public static final MediaType PROTOBUF_TYPE = new MediaType("application", "protobuf");

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return WritesProto.class.isAssignableFrom(aClass)
                && PROTOBUF_TYPE.isCompatible(mediaType);
    }

    @Override
    public void writeTo(WritesProto writesProto, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        writesProto.writeTo(outputStream);
    }

}
