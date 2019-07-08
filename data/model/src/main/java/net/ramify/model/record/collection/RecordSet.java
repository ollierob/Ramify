package net.ramify.model.record.collection;

import net.ramify.model.record.HasTitleDescription;

import javax.annotation.CheckForNull;

/**
 *
 */
public interface RecordSet extends HasTitleDescription, HasRecordSetId {

    @CheckForNull
    RecordSetId parentId();

}
