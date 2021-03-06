package net.ramify.model.record.xml.record;

import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;
import java.util.function.Function;

public abstract class XmlPersonOnDateWithFamilyRecord extends XmlPersonOnDateRecord {

    @XmlElementRef(required = false)
    private List<XmlRelationship> relationships;

    protected Family family(final RecordContext context) {
        return this.family(context, id -> this.events(id, context));
    }

    protected Family family(final RecordContext context, final Function<PersonId, ? extends PersonEventSet> createRootEvents) {
        final var root = this.person(context, createRootEvents);
        final var builder = new FamilyBuilder(root);
        this.addFamily(root, builder, context);
        return builder.build();
    }

    protected void addFamily(final Person root, final FamilyBuilder builder, final RecordContext context) {
        if (relationships == null) return;
        relationships.forEach(relationship -> relationship.addRelationship(root, builder, context));
    }

    @Override
    public int numIndividuals() {
        return super.numIndividuals() + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
