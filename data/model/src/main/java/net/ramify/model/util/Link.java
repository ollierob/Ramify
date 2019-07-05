package net.ramify.model.util;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.util.proto.LinkProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface Link extends BuildsProto<LinkProto.Link> {

    @Nonnull
    String href();

    @CheckForNull
    String text();

    @Nonnull
    default LinkProto.Link.Builder toProtoBuilder() {
        return LinkProto.Link.newBuilder()
                .setHref(this.href())
                .setText(MoreObjects.firstNonNull(this.text(), ""));
    }

    @Nonnull
    @Override
    default LinkProto.Link toProto() {
        return this.toProtoBuilder().build();
    }

}
