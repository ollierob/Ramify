package net.ramify.data.proto;

import com.google.protobuf.Message;

import javax.annotation.Nonnull;

public interface BuildsProto<M extends Message> {

    @Nonnull
    M toProto();

}
