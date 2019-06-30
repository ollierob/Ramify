package net.ramify.server.resource.jaxrs;

import com.google.inject.AbstractModule;

public class JaxrsModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(ProtobufStringMessageBodyWriter.class);
    }

}
