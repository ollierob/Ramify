package net.ramify.model.record.xml.collection;

import com.google.common.collect.Sets;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.ramify.model.record.xml.record.XmlRecord.NAMESPACE;

@XmlType(namespace = NAMESPACE, name = "recordSets")
@XmlRootElement(name = "recordSets")
public class XmlRecordSets {

    @XmlElementRef
    private List<XmlRecordSet> recordSets;

    @Nonnull
    public Set<DefaultRecordSet> recordSets(final RecordSetProvider recordSets, final RecordContext context) {
        if (this.recordSets == null) return Collections.emptySet();
        final var sets = Sets.<DefaultRecordSet>newLinkedHashSet();
        this.recordSets.forEach(rs -> sets.addAll(rs.build(recordSets, context)));
        return sets;
    }

    @Nonnull
    public Collection<Record> records(final RecordSetId id, final RecordContext context, final RecordSetProvider recordSetProvider) {
        if (this.recordSets == null || this.recordSets.isEmpty()) return Collections.emptySet();
        return this.recordSets.stream()
                .filter(Objects::nonNull)
                .flatMap(set -> set.records(context, recordSetProvider).stream())
                .filter(record -> record.recordSetId().equals(id))
                .collect(Collectors.toList());
    }

}
