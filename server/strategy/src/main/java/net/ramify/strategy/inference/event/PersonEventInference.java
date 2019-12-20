package net.ramify.strategy.inference.event;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.strategy.Inference;

public interface PersonEventInference<E extends Event> extends Inference<PersonEventSet, E> {

}
