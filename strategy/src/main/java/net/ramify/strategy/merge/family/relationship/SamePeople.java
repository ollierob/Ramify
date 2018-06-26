package net.ramify.strategy.merge.family.relationship;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.strategy.merge.person.PersonalAttributesMerge;

import javax.annotation.Nonnull;
import java.util.Optional;

class SamePeople implements RelationshipMerge {

    private final PersonalAttributesMerge personMerge;

    SamePeople(final PersonalAttributesMerge personMerge) {
        this.personMerge = personMerge;
    }

    @Nonnull
    @Override
    public Optional<Relationship> merge(@Nonnull final Relationship r1, @Nonnull final Relationship r2) {
//        final Optional<Person> p1 = personMerge.merge(r1.from(), r2.from());
//        if (p1.isPresent()) {
//            final Optional<Person> p2 = personMerge.merge(r1.to(), r2.to());
//            return p2.isPresent() ? Optional.of(r1.replace(p1.get(), p2.get())) : Optional.empty();
//        }
//        final Optional<Person> p3 = personMerge.merge(r1.from(), r2.to());
//        if (p3.isPresent()) {
//            final Optional<Person> p4 = personMerge.merge(r1.to(), r2.from());
//            return p4.isPresent() ? Optional.of(r1.replace(p3.get(), p4.get())) : Optional.empty();
//        }
//        return Optional.empty();
        throw new UnsupportedOperationException();
    }

}
