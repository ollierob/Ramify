package net.ramify.model.event.xml.historic;

import net.meerkat.collections.list.Lists;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "historicEvents")
public class XmlHistoricEvents {

    @XmlElementRef
    private List<XmlHistoricEvent> events;

    public Collection<HistoricEvent> build(final RecordContext context) {
        if (events == null) return Collections.emptyList();
        return Lists.eagerlyTransform(events, event -> event.build(context));
    }

}
