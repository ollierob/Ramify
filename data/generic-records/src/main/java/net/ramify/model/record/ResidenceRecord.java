package net.ramify.model.record;

import net.ramify.model.person.Person;
import net.ramify.model.person.event.Residence;

public class ResidenceRecord extends EventRecord<Residence> {

    public ResidenceRecord(final Person person, final Residence residence) {
        super(person, residence);
    }

}
