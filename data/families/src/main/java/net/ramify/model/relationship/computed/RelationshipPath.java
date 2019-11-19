package net.ramify.model.relationship.computed;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;
import net.ramify.model.relationship.type.IndirectRelationship;
import net.ramify.utils.collections.IterableUtils;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class RelationshipPath implements IndirectRelationship {

    public static RelationshipPath of(final Relationship relationship) {
        return new RelationshipPath(ImmutableList.of(relationship));
    }

    private final List<Relationship> relationships;

    public RelationshipPath(final List<Relationship> relationships) {
        Preconditions.checkArgument(!relationships.isEmpty());
        this.relationships = ImmutableList.copyOf(relationships);
    }

    @Nonnull
    @Override
    public PersonId fromId() {
        return relationships.get(0).fromId();
    }

    @Nonnull
    @Override
    public PersonId toId() {
        return relationships.get(relationships.size() - 1).toId();
    }

    @Nonnull
    @Override
    public List<Relationship> inferredRelationships() {
        return Collections.unmodifiableList(relationships);
    }

    @Override
    public boolean isUnknown() {
        return IterableUtils.any(relationships, Relationship::isUnknown);
    }

    @Override
    public boolean isDirected() {
        return IterableUtils.all(relationships, Relationship::isDirected);
    }

    @Nonnull
    @Override
    public RelationshipPath inverse() {
        return new RelationshipPath(ListUtils.eagerlyTransform(ListUtils.reversed(relationships), Relationship::inverse));
    }

    public RelationshipPath append(final PersonId next, final RelationshipFactory factory) {
        final var relationship = factory.relationshipBetween(this.toId(), next);
        return new RelationshipPath(ListUtils.suffix(relationships, relationship));
    }

}
