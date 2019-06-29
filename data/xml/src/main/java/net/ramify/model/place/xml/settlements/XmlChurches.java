package net.ramify.model.place.xml.settlements;

import com.google.common.collect.Lists;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.provider.PlaceProvider;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;

@XmlType(namespace = "http://ramify.net/places", name = "churches")
@XmlRootElement(name = "churches")
public class XmlChurches {

    @XmlElement(name = "church")
    private List<XmlChurch> churches;

    public Collection<Church> resolve(
            final PlaceProvider placeProvider,
            final DateParser dateParser) {
        return Lists.transform(churches, c -> c.resolve(placeProvider, dateParser));
    }

}
