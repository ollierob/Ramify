package net.ramify.server.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.List;

class Bundle {

    static final TypeReference<HashMap<String, Bundle>> TYPE_REFERENCE = new TypeReference<HashMap<String, Bundle>>() {

    };

    @JsonProperty("js")
    List<String> jsBundleFiles;

}
