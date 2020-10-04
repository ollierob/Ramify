package net.ramify.model.record.xml.record;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.xml.person.XmlPersonEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.person.xml.feature.XmlPersonFeature;
import net.ramify.model.person.xml.name.XmlName;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static net.ramify.utils.StringUtils.isBlank;

public class XmlPersonRecord extends XmlRecord {

    @XmlAttribute(name = "personId")
    private String personId = UUID.randomUUID().toString();

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef(required = false)
    private XmlName complexName;

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @XmlAttribute(name = "notes")
    private String notes;

    @XmlAttribute(name = "occupation")
    private String occupation;

    @XmlElementRef(required = false)
    private List<XmlPersonFeature> features;

    @XmlElementRef(required = false)
    private List<XmlPersonEvent> events;

    public PersonId personId() {
        return new PersonId(personId);
    }

    @Nonnull
    protected Name name(final NameParser parser) {
        if (complexName != null) return complexName.build(parser);
        if (!isBlank(name)) return parser.parse(name);
        return Name.UNKNOWN;
    }

    @Nonnull
    protected Gender gender() {
        return Functions.ifNonNull(gender, XmlGender::gender, Gender.UNKNOWN);
    }

    protected Sex sex() {
        return gender.sex();
    }

    @CheckForNull
    protected String notes() {
        return notes;
    }

    @CheckForNull
    protected String occupation() {
        return occupation;
    }

    @Nonnull
    protected Set<PersonFeature> features() {
        return features == null
                ? ImmutableSet.of()
                : SetUtils.eagerTransform(features, XmlPersonFeature::toFeature);
    }

    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = new MutablePersonEventSet(context.uniqueEventMerger());
        this.events().forEach(event -> events.addAll(event.allEvents(personId, context, false)));
        return events;
    }

    protected List<XmlPersonEvent> events() {
        return MoreObjects.firstNonNull(events, Collections.emptyList());
    }

    @Override
    public int numIndividuals() {
        return 1;
    }

}
