package net.ramify.model.place.xml.church;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "churchInfos")
@XmlRootElement(name = "churchInfos")
public class XmlChurchInfos {

    @XmlElementRef
    private List<XmlChurchInfo> churches;

    public Collection<ChurchInfo> resolve(final PlaceProvider placeProvider, final DateParser dateParser) {
        Preconditions.checkState(churches != null);
        return Lists.transform(churches, c -> c.resolve(placeProvider, dateParser));
    }

}
