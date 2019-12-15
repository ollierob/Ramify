package net.ramify.strategy.merge.event;

import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.strategy.merge.Merger;

public interface EventsMerger extends Merger<HasPersonEvents, PersonEventSet> {

}
