package net.ramify.model.relationship.computed;

import com.google.common.base.Preconditions;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.IndirectRelationship;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.List;

public class RelationshipPath implements IndirectRelationship {

    private final List<Relationship> relationships;

    public RelationshipPath(final List<Relationship> relationships) {
        Preconditions.checkArgument(!relationships.isEmpty());
        this.relationships = relationships;
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
    public List<Relationship> relationships() {
        return relationships;
    }

    @Override
    public boolean isDirected() {
        return IterableUtils.any(relationships, Relationship::isDirected);
    }

}