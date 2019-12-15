package net.ramify.model.event.merge;

import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.strategy.Merger;

public interface EventsMerger extends Merger<HasPersonEvents, PersonEventSet> {

}
