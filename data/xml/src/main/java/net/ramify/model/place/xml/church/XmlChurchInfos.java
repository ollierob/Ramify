package net.ramify.model.place.xml.church;

import com.google.common.collect.Lists;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.model.place.provider.PlaceProvider;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;

@XmlType(namespace = "http://ramify.net/places", name = "churches")
@XmlRootElement(name = "churches")
public class XmlChurchInfos {

    @XmlElement(name = "church")
    private List<XmlChurchInfo> churches;

    public Collection<ChurchInfo> resolve(
            final PlaceProvider placeProvider,
            final DateParser dateParser) {
        return Lists.transform(churches, c -> c.resolve(placeProvider, dateParser));
    }

}
