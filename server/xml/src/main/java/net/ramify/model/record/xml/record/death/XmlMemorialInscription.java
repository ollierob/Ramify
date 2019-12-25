package net.ramify.model.record.xml.record.death;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.family.Family;
import net.ramify.model.family.xml.XmlFamily;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericDeathRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.DeathRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.collections.ListUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "memorialInscription")
public class XmlMemorialInscription extends XmlRecord {

    @XmlElement(name = "text", namespace = XmlRecord.NAMESPACE, required = true)
    private String text;

    @XmlElementRef
    private List<XmlFamily> families;

    @Override
    public int numIndividuals() {
        if (families == null || families.isEmpty()) return 0;
        return families.stream().mapToInt(XmlFamily::numPeople).sum();
    }

    public Collection<DeathRecord> build(final PlaceId coversId, final RecordContext context, final RecordSet recordSet) {
        if (families == null) return Collections.emptySet();
        return ListUtils.eagerlyTransform(families, family -> this.buildDeathRecord(family.toFamily(context), coversId, recordSet));
    }

    private DeathRecord buildDeathRecord(final Family family, final PlaceId coversId, final RecordSet recordSet) {
        final var date = this.deathRange(family);
        return new GenericDeathRecord(
                this.recordId(),
                recordSet,
                date,
                coversId,
                family);
    }

    private DateRange deathRange(final Family family) {
        return ClosedDateRange.of(family.events(DeathEvent.class));
    }

}
