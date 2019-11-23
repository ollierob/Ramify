package net.ramify.strategy.event.merge;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.strategy.merge.Merger;

import java.util.Set;

public interface EventsMerger extends Merger<HasPersonEvents, Set<? extends Event>> {

}
