package net.ramify.model.record.collection;

import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.group.HasRecordSetGroupId;
import net.ramify.model.record.group.RecordSetGroup;

/**
 * @see RecordSetGroup
 */
public interface RecordSet extends HasTitleDescription, HasRecordSetId, HasRecordSetGroupId {

}
