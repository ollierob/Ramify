package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.marriage.MarriageEvent;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;

public class XmlMarriagePartner extends XmlPersonOnDateWithFamilyRecord {

    protected Person toPerson(final RecordContext context, final MarriageEvent marriage) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(context.nameParser()),
                this.gender(),
                this.events(personId, marriage, context),
                null);
    }

    protected MutablePersonEventSet events(final PersonId personId, final MarriageEvent marriage, final RecordContext context) {
        final var events = this.events(personId, marriage.date(), context);
        events.add(marriage);
        return events;
    }

}
