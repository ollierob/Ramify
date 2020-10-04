package net.ramify.model.family;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.NoPersonEvents;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.GenericPerson;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static net.ramify.model.family.GedcomParser.*;

class Gedcom55PersonBuilder implements GedcomFamilyLineReader {

    private final List<Gedcom55EventBuilder> events = new LinkedList<>();
    private final String id;
    private final BiConsumer<String, Gedcom55PersonBuilder> addToFamily;

    private GedcomFamilyLineReader reader;
    private Name name;
    private Gender gender;

    Gedcom55PersonBuilder(final String id, final BiConsumer<String, Gedcom55PersonBuilder> addToFamily) {
        this.id = id;
        this.addToFamily = addToFamily;
    }

    String id() {
        return id;
    }

    @Override
    public void read(final int level, final int start, final String line) {
        switch (level) {
            case 0:
                throw new IllegalArgumentException();
            case 1:
                reader = null;
                final var next = line.indexOf(' ', start + 1);
                final var type = next > 0 ? line.substring(start, next) : line.substring(start);
                switch (type) {
                    case "NAME" -> this.startBio(line.substring(next + 1));
                    case "SEX" -> this.setSex(line.substring(next + 1));
                    case "FAMS", "FAMC" -> this.addToFamily(line.substring(next + 1));
                    //Events
                    case "BIRT" -> this.startEvent(EventBuilder::toBirth);
                    case "RESI" -> this.startEvent(EventBuilder::toResidence);
                    case "DEAT" -> this.startEvent(EventBuilder::toDeath);
                    case "BURI" -> this.startEvent(EventBuilder::toBurial);
                }
                break;
            default:
                if (reader != null) reader.read(level, start, line);
        }
    }

    private void startEvent(final BiFunction<EventBuilder, PersonId, ? extends PersonEvent> create) {
        final var reader = new EventReader(create);
        this.reader = reader;
        events.add(reader);
    }

    private void startBio(final String name) {
        this.name = parseName(name);
        reader = new BioReader();
    }

    private void setSex(final String sex) {
        this.gender = parseSex(sex);
    }

    private void addToFamily(final String familyId) {
        addToFamily.accept(familyId, this);
    }

    private PersonEventSet events(final PersonId personId) {
        if (events.isEmpty()) return NoPersonEvents.INSTANCE;
        final var set = MutablePersonEventSet.notPermittingMerge();
        events.forEach(event -> set.add(event.build(personId)));
        return set;
    }

    Person build() {
        final var id = personId(this.id);
        final var events = this.events(id);
        return new GenericPerson(id, name, gender, events);
    }

    private final class BioReader implements GedcomFamilyLineReader {

        @Override
        public void read(final int level, final int start, final String line) {
            //TODO
        }

    }

    private final class EventReader extends Gedcom55EventBuilder {

        private final BiFunction<EventBuilder, PersonId, ? extends PersonEvent> createEvent;

        private EventReader(final BiFunction<EventBuilder, PersonId, ? extends PersonEvent> createEvent) {
            this.createEvent = createEvent;
        }

        @Override
        PersonEvent build(final PersonId personId) {
            return createEvent.apply(this.eventBuilder(), personId);
        }

    }

}
