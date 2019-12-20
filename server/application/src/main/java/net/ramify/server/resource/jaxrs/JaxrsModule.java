package net.ramify.server.resource.jaxrs;

import com.google.inject.AbstractModule;

public class JaxrsModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(ProtobufCompatibleMessageBodyWriter.class);
        this.bind(ProtobufDirectMessageBodyWriter.class);
        this.bind(ProtobufDirectMessageBodyReader.class);
        this.bind(ProtobufStringMessageBodyWriter.class);
        this.bind(CacheControlFilter.class);
        this.bind(DateParamConverterProvider.class);
        this.bind(DateRangeParamConverterProvider.class);
    }

}
