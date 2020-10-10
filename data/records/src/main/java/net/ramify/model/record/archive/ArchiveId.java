package net.ramify.model.record.archive;

import net.ramify.model.Id;

public class ArchiveId extends Id {

    public static final ArchiveId PRIVATE = new ArchiveId("private");

    public ArchiveId(final String value) {
        super(value);
    }

}
