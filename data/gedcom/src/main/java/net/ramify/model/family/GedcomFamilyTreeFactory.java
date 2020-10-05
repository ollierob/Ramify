package net.ramify.model.family;

import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.place.provider.PlaceParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Singleton
public class GedcomFamilyTreeFactory {

    private final DateRangeParser dateParser;
    private final PlaceParser placeParser;

    @Inject
    public GedcomFamilyTreeFactory(final DateRangeParser dateParser, final PlaceParser placeParser) {
        this.dateParser = dateParser;
        this.placeParser = placeParser;
    }

    public FamilyTree create(final FamilyTreeId id, final File gedcom) throws IOException {
        try (final var reader = new BufferedReader(new FileReader(gedcom))) {
            return this.create(id, reader);
        }
    }

    public FamilyTree create(final FamilyTreeId id, final BufferedReader reader) throws IOException {

        String line;
        GedcomHeaderReader header = null;
        GedcomFamilyTreeBuilder family = null;

        while ((line = reader.readLine()) != null) {

            final var space = line.indexOf(' ');
            final var level = level(line, space);

            if (level == 0) {
                if (header == null && family == null) {
                    header = new GedcomHeaderReader(dateParser, placeParser);
                } else if (family == null) {
                    family = header.familyBuilder(id);
                    header = null;
                }
            }

            if (header != null) header.read(level, space + 1, line);
            else if (family != null) family.read(level, space + 1, line);

        }

        return family.build();

    }

    private static int level(final String line, final int space) {
        return switch (space) {
            case -1, 0 -> throw new IllegalArgumentException("No level: " + line);
            case 1 -> Character.digit(line.charAt(0), 10);
            default -> Integer.parseInt(line.substring(0, space));
        };
    }

}
