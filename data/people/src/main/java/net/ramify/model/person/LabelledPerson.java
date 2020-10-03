package net.ramify.model.person;

import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;
import java.util.Set;

public class LabelledPerson extends DelegatedPerson {

    private final Set<String> labels;

    public LabelledPerson(final Person person, final Set<String> labels) {
        super(person);
        this.labels = labels;
    }

    @Nonnull
    @Override
    public PersonProto.Person.Builder toProtoBuilder() {
        final var builder = super.toProtoBuilder();
        labels.forEach(builder::addLabel);
        return builder;
    }

}
