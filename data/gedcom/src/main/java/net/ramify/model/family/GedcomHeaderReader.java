package net.ramify.model.family;

import com.google.common.base.Preconditions;
import net.ramify.model.family.tree.FamilyTreeId;

class GedcomHeaderReader {

    private String level1;
    private String encoding;
    private String version;

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
        if ("5.5".equals(version)) return new Gedcom55FamilyTreeBuilder(id);
        throw new UnsupportedOperationException("Version " + version);
    }

}
