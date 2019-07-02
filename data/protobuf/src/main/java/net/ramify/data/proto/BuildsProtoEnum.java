package net.ramify.data.proto;

import com.google.protobuf.ProtocolMessageEnum;

import javax.annotation.Nonnull;

public interface BuildsProtoEnum<E extends ProtocolMessageEnum> {

    @Nonnull
    E toProto();

}
