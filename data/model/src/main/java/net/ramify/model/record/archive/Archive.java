package net.ramify.model.record.archive;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.proto.ArchiveProto;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * Any holder of records.
 *
 * @see net.ramify.model.record.collection.RecordSetReference
 */
public class Archive implements HasArchiveId, HasTitleDescription, BuildsProto<ArchiveProto.Archive> {

    private final ArchiveId id;
    private final String name;
    private final String description;
    private final Link website;
    private final String iconHref;

    public Archive(final ArchiveId id, final String name, final String description, final Link website, final String iconHref) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.iconHref = iconHref;
    }

    @Nonnull
    @Override
    public ArchiveId archiveId() {
        return id;
    }

    @Nonnull
    @Override
    public String title() {
        return name;
    }

    @CheckForNull
    @Override
    public String description() {
        return description;
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
                .setName(name)
                .setWebsite(website.toProto())
                .setIcon(MoreObjects.firstNonNull(iconHref, ""));
    }

}
