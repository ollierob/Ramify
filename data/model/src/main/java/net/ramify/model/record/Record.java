package net.ramify.model.record;

import net.ramify.model.date.HasDate;
import net.ramify.model.person.collection.HasPeople;
import net.ramify.utils.objects.Castable;

public interface Record extends HasRecordId, HasDate, HasPeople, Castable<Record> {

}
