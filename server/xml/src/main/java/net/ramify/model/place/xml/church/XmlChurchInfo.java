package net.ramify.model.place.xml.church;

import com.google.common.collect.Sets;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.institution.church.ChurchInfo;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
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

    @XmlElementRef
    private List<XmlChurchRecordSetInfo> recordSets;

    @Override
    public PlaceId placeId() {
        return new Spid(iso, Church.class, placeId);
    }

    public String denomination() {
        return denomination;
    }

    @Nonnull
    private Set<RecordSet> recordSets(final RecordSetProvider records) {
        if (recordSets == null) return Collections.emptySet();
        return SetUtils.transformIgnoreNull(recordSets, set -> set.build(records), Sets::newLinkedHashSetWithExpectedSize);
    }

    @Nonnull
    ChurchInfo resolve(final PlaceProvider places, final RecordSetProvider records, final DateParser dateParser) {
        final var placeId = this.placeId();
        try {
            return new ResolvedChurchInfo(
                    places.require(placeId, Church.class),
                    denomination,
                    this.recordSets(records));
        } catch (final Exception ex) {
            throw new IllegalArgumentException("Error creating church info for " + placeId, ex);
        }
    }

}
