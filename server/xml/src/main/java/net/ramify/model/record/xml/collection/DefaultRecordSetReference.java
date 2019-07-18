package net.ramify.model.record.xml.collection;

import net.ramify.model.record.archive.Archive;
import net.ramify.model.record.collection.RecordSetReference;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
class DefaultRecordSetReference implements RecordSetReference {

    private final String reference;
    private final Archive archive;
    private final Link link;

    DefaultRecordSetReference(final String reference, final Archive archive, final Link link) {
        this.reference = reference;
        this.archive = archive;
        this.link = link;
    }

    @Nonnull
    @Override
    public String reference() {
        return reference;
    }

    @Nonnull
    @Override
    public Archive archive() {
        return archive;
    }

    @CheckForNull
    @Override
    public Link link() {
        return link;
    }

}
