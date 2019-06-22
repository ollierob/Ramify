package net.ramify.model.person;

import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.person.gender.HasGender;
import net.ramify.model.person.name.HasName;

public interface Person extends HasName, HasGender, HasPersonEvents {

}
