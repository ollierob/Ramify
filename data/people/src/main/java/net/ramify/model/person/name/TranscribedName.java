package net.ramify.model.person.name;

import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public class TranscribedName implements Name {

    private final String original;
    private final Name assumed;

    public TranscribedName(final String original, final Name assumed) {
        this.original = original;
        this.assumed = assumed;
    }

    @Nonnull
    @Override
    public String value() {
        return assumed.value();
    }

    public Name assumed() {
        return assumed;
    }

    @Override
    public boolean contains(final String name) {
        final var lc = name.toLowerCase();
        return assumed.contains(lc) || original.toLowerCase().contains(lc);
    }

    @Nonnull
    @Override
    public PersonProto.Name.Builder toProtoBuilder() {
        return assumed.toProtoBuilder().setOriginal(original);
    }

}
