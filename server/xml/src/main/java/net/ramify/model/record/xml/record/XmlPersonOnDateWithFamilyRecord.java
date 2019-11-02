package net.ramify.model.record.xml.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class XmlPersonOnDateWithFamilyRecord extends XmlPersonOnDateRecord {

    @XmlElementRef
    private List<XmlRelationship> relationships;

    protected Family family(final RecordContext context, final DateRange recordDate) {
        return this.family(context, recordDate, id -> this.events(id, recordDate, context));
    }

    protected Family family(final RecordContext context, final DateRange recordDate, final Function<PersonId, Set<? extends Event>> createEvents) {
        final var root = this.person(recordDate, context, createEvents);
        final var builder = new FamilyBuilder(root);
        this.addFamily(root, builder, context, recordDate);
        return builder.build();
    }

    void addFamily(final Person root, final FamilyBuilder builder, final RecordContext context, final DateRange recordDate) {
        if (relationships == null) return;
        relationships.forEach(relationship -> relationship.addRelationship(root, builder, context, recordDate));
    }

    public int numIndividuals() {
        return 1 + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
