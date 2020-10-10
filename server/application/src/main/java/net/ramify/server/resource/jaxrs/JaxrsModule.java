package net.ramify.server.resource.jaxrs;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.ollie.protobuf.jaxrs.ProtobufCompatibleMessageBodyWriter;
import net.ollie.protobuf.jaxrs.ProtobufDirectMessageBodyReader;
import net.ollie.protobuf.jaxrs.ProtobufDirectMessageBodyWriter;
import net.ollie.protobuf.jaxrs.ProtobufStringMessageBodyWriter;
import net.ramify.model.record.proto.RecordProto;

import javax.inject.Singleton;

public class JaxrsModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(ProtobufCompatibleMessageBodyWriter.class);
        this.bind(ProtobufDirectMessageBodyWriter.class);
        this.bind(ProtobufStringMessageBodyWriter.class);
        this.bind(CacheControlFilter.class);
        this.bind(DateParamConverterProvider.class);
        this.bind(DateRangeParamConverterProvider.class);
        this.bind(AdhocParamConverterProvider.class);
        this.bind(NotLoggedInExceptionMapper.class);
    }

    @Provides
    @Singleton
    ProtobufDirectMessageBodyReader directReader() {
        final var reader = new ProtobufDirectMessageBodyReader();
        reader.register(RecordProto.RecordSearch.class, RecordProto.RecordSearch::parseFrom);
        return reader;
    }

}
