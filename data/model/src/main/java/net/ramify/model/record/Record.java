package net.ramify.model.record;

import net.ramify.model.Castable;
import net.ramify.model.date.HasDate;
import net.ramify.model.person.collection.HasPeople;

public interface Record extends HasRecordId, HasDate, HasPeople, Castable<Record> {

}
