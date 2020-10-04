package net.ramify.model.family;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class UnionFamily implements Family {

    private final Family left, right;
    private final Relationship union;

    public UnionFamily(final Family left, final Family right, final Relationship union) {
        this.left = left;
        this.right = right;
        this.union = union;
    }

    @Nonnull
    @Override
    public Person root() {
        return MoreObjects.firstNonNull(left.root(), right.root());
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return Sets.union(left.people(), right.people());
    }

    @Nonnull
    @Override
    public Set<? extends Relationship> relationships() {
        return SetUtils.with(Sets.union(left.relationships(), right.relationships()), union);
    }

    @Nonnull
    @Override
    public Optional<Relationship> relationshipBetween(final PersonId from, final PersonId to) {
        if (union.fromId().equals(from) && union.toId().equals(to)) return Optional.of(union);
        return left.relationshipBetween(from, to).or(() -> right.relationshipBetween(from, to));
    }

}
