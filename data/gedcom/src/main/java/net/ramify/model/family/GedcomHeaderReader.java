package net.ramify.model.family;

import com.google.common.base.Preconditions;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.place.provider.PlaceParser;

class GedcomHeaderReader {

    private final DateRangeParser dateParser;
    private final PlaceParser placeParser;
    private String level1;
    private String encoding;
    private String version;

    GedcomHeaderReader(final DateRangeParser dateParser, final PlaceParser placeParser) {
        this.dateParser = dateParser;
        this.placeParser = placeParser;
    }

    void read(final int level, final int start, final String line) {
        final String substring = line.substring(start);
        switch (level) {
            case 0:
                Preconditions.checkArgument("HEAD".equals(substring), "Not a header: %s", line);
                break;
            case 1:
                level1 = substring;
                if (level1.startsWith("CHAR")) encoding = level1.substring(5);
                break;
            case 2:
                if (level1.equals("GEDC") && substring.startsWith("VERS")) version = substring.substring(5);
        }
    }

    GedcomFamilyTreeBuilder familyBuilder(final FamilyTreeId id) {
        if ("5.5".equals(version)) return new Gedcom55FamilyTreeBuilder(dateParser, placeParser, id);
        throw new UnsupportedOperationException("Version " + version);
    }

}
