package net.ramify.strategy.join.relationship;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.strategy.join.Join;

public interface RelationshipJoin extends Join<Relationship> {

    @Override
    boolean join(Relationship r1, Relationship r2);

}
