package net.ramify.model.person;

import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public class NotedPerson extends DelegatedPerson {

    private final String notes;

    public NotedPerson(final Person person, final String notes) {
        super(person);
        this.notes = notes;
    }

    @Nonnull
    @Override
    public PersonProto.Person.Builder toProtoBuilder() {
        final var builder = super.toProtoBuilder();
        if (notes != null) builder.setNotes(notes);
        return builder;
    }

}
