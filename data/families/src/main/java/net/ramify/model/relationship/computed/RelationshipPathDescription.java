package net.ramify.model.relationship.computed;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.AffineRelationship;
import net.ramify.model.relationship.type.CosanguinealRelationship;
import net.ramify.model.relationship.type.FictiveRelationship;
import net.ramify.model.relationship.type.IndirectRelationship;
import net.ramify.model.relationship.type.RelationshipHandler;
import net.ramify.model.relationship.type.UnknownRelationship;

import java.util.List;

class RelationshipPathDescription {

    static String describeFrom(final RelationshipPath path) {
        switch (path.size()) {
            case 0:
                throw new IllegalArgumentException();
            case 1:
                return path.get(0).describeFrom();
            default:
                return describeFrom(path.inferredRelationships());
        }
    }

    static String describeFrom(final List<Relationship> relationships) {
        final var describer = new RelationshipDescriber();
        relationships.forEach(describer::handle);
        return describer.describe();
    }

    private static class RelationshipDescriber implements RelationshipHandler<Relationship> {

        private static final Joiner FLATTEN = Joiner.on('/');

        private boolean anyUnknown;
        private final List<String> descriptions = Lists.newArrayList();

        @Override
        public Relationship handle(final AffineRelationship relationship) {
            descriptions.add(relationship.describeFrom());
            return relationship;
        }

        @Override
        public Relationship handle(final CosanguinealRelationship relationship) {
            descriptions.add(relationship.describeFrom());
            return relationship;
        }

        @Override
        public Relationship handle(final FictiveRelationship relationship) {
            descriptions.add(relationship.describeFrom());
            return relationship;
        }

        @Override
        public Relationship handle(final IndirectRelationship relationship) {
            relationship.inferredRelationships().forEach(this::handle);
            return relationship;
        }

        @Override
        public Relationship handle(final UnknownRelationship relationship) {
            anyUnknown = true;
            return relationship;
        }

        String describe() {
            if (anyUnknown) return "Unknown";
            return FLATTEN.join(descriptions); //FIXME
        }

    }

}
