package net.ramify.model.place.xml.place.group;

import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.group.DefaultPlaceGroup;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.utils.collections.SetUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeGroup")
public class XmlPlaceGroup {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "defaultChildId", required = true)
    private String defaultChildId;

    @XmlElement(name = "childId", namespace = XmlPlace.NAMESPACE)
    private Set<String> childIds;

    PlaceGroup toPlaceGroup() {
        return new DefaultPlaceGroup(
                new PlaceGroupId(id),
                name,
                new PlaceId(defaultChildId),
                SetUtils.transform(childIds, PlaceId::new));
    }

}
