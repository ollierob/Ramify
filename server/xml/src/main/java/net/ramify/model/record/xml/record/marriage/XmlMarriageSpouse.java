package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.marriage.MarriageEvent;
import net.ramify.model.family.Family;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.feature.Literacy;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.mention.XmlSignature;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Set;

import static net.ramify.utils.StringUtils.isBlank;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "marriageSpouse")
public class XmlMarriageSpouse extends XmlPersonOnDateWithFamilyRecord {

    @XmlAttribute(name = "signed")
    private XmlSignature signature;

    protected Family family(final RecordContext context, final EventId marriageEventId) {
        return super.family(context, id -> this.events(id, context, marriageEventId));
    }

    protected MarriageEvent marriage(final EventId eventId, final PersonId personId, final DateRange date) {
        return this.eventBuilder(eventId, date).toMarriage(personId);
    }

    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context, final EventId marriageEventId) {
        final var events = super.events(personId, context);
        events.add(this.marriage(marriageEventId, personId, context.recordDate()));
        return events;
    }

    @CheckForNull
    @Override
    protected String notes() {
        final var notes = super.notes();
        if (!isBlank(notes)) return notes;
        if (signature == XmlSignature.SIGNATURE) return "Signed name";
        if (signature == XmlSignature.MARK) return "Marked name X";
        return null;
    }

    @Nonnull
    @Override
    protected Set<PersonFeature> features() {
        final var features = super.features();
        return signature == null
                ? features
                : SetUtils.with(features, new Literacy(signature.isLiterate()));
    }

}
