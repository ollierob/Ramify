package net.ramify.model.family;

import net.ramify.model.event.collection.NoPersonEvents;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.GenericPerson;
import net.ramify.model.person.Person;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

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
                final var type = line.substring(start, next);
                switch (type) {
                    case "BIRT" -> this.startBirth();
                    case "RESI" -> this.startResidence();
                    case "NAME" -> this.startBio(line.substring(next + 1));
                    case "SEX" -> this.setSex(line.substring(next + 1));
                    case "FAMS", "FAMC" -> this.addToFamily(line.substring(next + 1));
                }
                break;
            default:
                if (reader != null) reader.read(level, start, line);
        }
    }

    private void startBirth() {
        final var reader = new BirthReader();
        this.reader = reader;
        events.add(reader);
    }

    private void startResidence() {
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

    private PersonEventSet events() {
        if (events.isEmpty()) return NoPersonEvents.INSTANCE;
        throw new UnsupportedOperationException();
    }

    Person build() {
        final var id = personId(this.id);
        final var events = this.events();
        return new GenericPerson(id, name, gender, events);
    }

    private final class BioReader implements GedcomFamilyLineReader {

        @Override
        public void read(final int level, final int start, final String line) {
            //TODO
        }

    }

    private final class BirthReader extends Gedcom55EventBuilder {

        @Override
        BirthEvent build() {
            throw new UnsupportedOperationException(); //TODO
        }

    }

}
