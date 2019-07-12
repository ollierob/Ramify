package net.ramify.model.place.xml.church;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBeforeDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "churchInfo")
public class XmlChurchInfo implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String placeId;

    @XmlAttribute(name = "denomination")
    private String denomination;

    @XmlElements({
            @XmlElement(name = "foundedBefore", type = XmlBeforeDate.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "foundedIn", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange founded;

    @XmlElements({
            @XmlElement(name = "closedBefore", type = XmlBeforeDate.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedIn", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange closed;

    @XmlElementRef
    private List<XmlChurchRecordSetInfo> recordSets;

    @Override
    public PlaceId placeId() {
        return new Spid(Church.class, placeId);
    }

    public String denomination() {
        return denomination;
    }

    @Nonnull
    private DateRange founded(final DateParser dateParser) {
        return founded.resolve(dateParser);
    }

    @CheckForNull
    private DateRange closed(final DateParser parser) {
        return Functions.ifNonNull(closed, c -> c.resolve(parser));
    }

    @Nonnull
    private Set<RecordSet> recordSets(final RecordSetProvider records) {
        if (recordSets == null) return Collections.emptySet();
        return SetUtils.transformIgnoreNull(recordSets, set -> set.build(records), Sets::newLinkedHashSetWithExpectedSize);
    }

    @Nonnull
    ChurchInfo resolve(final PlaceProvider places, final RecordSetProvider records, final DateParser dateParser) {
        try {
            return new ResolvedChurchInfo(
                    places.require(this.placeId(), Church.class),
                    denomination,
                    this.founded(dateParser),
                    this.closed(dateParser),
                    this.recordSets(records));
        } catch (final Place.InvalidPlaceTypeException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
