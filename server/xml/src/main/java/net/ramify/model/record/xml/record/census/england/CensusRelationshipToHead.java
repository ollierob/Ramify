package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;
import net.ramify.model.relationship.type.ChildParent;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = XmlRecord.NAMESPACE)
public enum CensusRelationshipToHead implements RelationshipFactory {

    @XmlEnumValue("head")
    HEAD {
        @Nonnull
        @Override
        public Relationship relationshipBetween(HasPersonId from, HasPersonId to) {
            throw new UnsupportedOperationException();
        }
    },

    @XmlEnumValue("spouse")
    SPOUSE {
        @Nonnull
        @Override
        public Relationship relationshipBetween(HasPersonId from, HasPersonId to) {
            return new Married(from, to);
        }
    },

    @XmlEnumValue("child")
    CHILD {
        @Nonnull
        @Override
        public Relationship relationshipBetween(HasPersonId from, HasPersonId to) {
            return new ParentChild(from, to);
        }
    },

    @XmlEnumValue("parent")
    PARENT {
        @Nonnull
        @Override
        public Relationship relationshipBetween(HasPersonId from, HasPersonId to) {
            return new ChildParent(from, to);
        }
    };

    public boolean isHead() {
        return this == HEAD;
    }

}
