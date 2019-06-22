package net.ramify.model.record;

import net.ramify.model.date.HasDate;
import net.ramify.model.family.collection.HasFamilies;
import net.ramify.utils.objects.Castable;

public interface Record extends HasRecordId, HasDate, HasFamilies, Castable<Record> {

}
