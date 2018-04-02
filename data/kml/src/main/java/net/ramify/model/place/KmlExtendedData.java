package net.ramify.model.place;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class KmlExtendedData {

    @XmlElement(name = "Data")
    private List<KmlData> data;

    KmlExtendedData(final List<KmlData> data) {
        this.data = data;
    }

    static KmlExtendedData of(final Map<String, String> map) {
        final List<KmlData> data = new ArrayList<>(map.size());
        map.forEach((key, value) -> data.add(new KmlData(key, value)));
        return new KmlExtendedData(data);
    }

    static class KmlData {

        @XmlAttribute(name = "name")
        private String name;

        @XmlElement(name = "value")
        private String value;

        KmlData(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

    }

}
