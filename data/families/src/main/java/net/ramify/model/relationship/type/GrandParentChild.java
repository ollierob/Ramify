package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class GrandParentChild extends AbstractRelationship implements CosanguinealRelationship {

    protected GrandParentChild(HasPersonId from, HasPersonId to) {
        super(from, to);
    }

    protected GrandParentChild(PersonId from, PersonId to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public List<Relationship> inferredRelationships() {
        final PersonId parentId = PersonId.random();
        return Arrays.asList(new ParentChild(this.fromId(), parentId), new ParentChild(parentId, this.toId()));
    }

}
