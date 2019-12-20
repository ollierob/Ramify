package net.ramify.strategy.inference.event;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.strategy.Inference;

public interface PersonEventInference<E extends PersonEvent> extends Inference<PersonEventSet, E> {

}
