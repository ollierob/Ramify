package net.ramify.model.record.archive;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.ArchiveProto;
import net.ramify.model.util.Link;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class Archive implements HasArchiveId, BuildsProto<ArchiveProto.Archive> {

    private final ArchiveId id;
    private final Link website;

    public Archive(final ArchiveId id, final Link website) {
        this.id = id;
        this.website = website;
    }

    @Nonnull
    @Override
    public ArchiveId archiveId() {
        return id;
    }

    @Nonnull
    @Override
    public ArchiveProto.Archive toProto() {
        return toProtoBuilder().build();
    }

    @OverridingMethodsMustInvokeSuper
    protected ArchiveProto.Archive.Builder toProtoBuilder() {
        return ArchiveProto.Archive.newBuilder()
                .setId(id.value())
                .setWebsite(website.toProto());
    }

}
