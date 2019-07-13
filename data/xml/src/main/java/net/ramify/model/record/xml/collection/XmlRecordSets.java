package net.ramify.model.record.xml.collection;

import com.google.common.collect.Sets;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static net.ramify.model.record.xml.record.XmlRecord.NAMESPACE;

@XmlType(namespace = NAMESPACE, name = "recordSets")
@XmlRootElement(name = "recordSets")
public class XmlRecordSets {

    @XmlElementRef
    private List<XmlRecordSet> recordSets;

    @Nonnull
    public Set<RecordSet> recordSets(final RecordSetProvider recordSets, final DateParser dateParser) {
        if (this.recordSets == null) return Collections.emptySet();
        final var sets = Sets.<RecordSet>newLinkedHashSet();
        this.recordSets.forEach(rs -> sets.addAll(rs.build(recordSets, dateParser)));
        return sets;
    }

}
