package net.ramify.model.place.xml.place.group;

import com.google.common.collect.Collections2;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placeGroups")
@XmlRootElement(name = "placeGroups")
public class XmlPlaceGroups {

    @XmlElementRef
    private List<XmlPlaceGroup> groups;

    @Nonnull
    public Collection<PlaceGroup> placeGroups(final PlaceProvider placeProvider) {
        if (groups == null || groups.isEmpty()) return Collections.emptySet();
        return Collections2.transform(groups, g -> g.toPlaceGroup(placeProvider));
    }

}
