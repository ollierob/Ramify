package net.ramify.model.relationship.type;

import com.google.common.collect.ImmutableList;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.List;

public class Siblings extends AbstractRelationship implements CosanguinealRelationship {

    public Siblings(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public String describeFrom() {
        return "Siblings";
    }

    @Nonnull
    @Override
    public String describeTo() {
        return "Siblings";
    }

    @Nonnull
    @Override
    public Siblings inverse() {
        return new Siblings(this.toId(), this.fromId());
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Nonnull
    @Override
    public List<Relationship> inferredRelationships() {
        final var parent = PersonId.random();
        return ImmutableList.of(new ChildParent(this.fromId(), parent), new ParentChild(parent, this.toId()));
    }

    @Override
    public <R> R handleWith(final RelationshipHandler<R> handler) {
        throw new UnsupportedOperationException(); //TODO
    }

}
