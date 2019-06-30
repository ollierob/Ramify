package net.ramify.server.resource;

import javax.ws.rs.core.MediaType;

public interface Resource {

    String APPLICATION_PROTOBUF = "application/protobuf";
    MediaType APPLICATION_PROTOBUF_TYPE = new MediaType("application", "protobuf");

}
