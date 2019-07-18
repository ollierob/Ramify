package net.ramify.model.record.archive;

import javax.annotation.Nonnull;

public interface HasArchive extends HasArchiveId {

    @Nonnull
    Archive archive();

    @Nonnull
    @Override
    default ArchiveId archiveId() {
        return this.archive().archiveId();
    }
    
}
