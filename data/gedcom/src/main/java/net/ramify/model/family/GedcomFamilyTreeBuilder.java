package net.ramify.model.family;

import net.ramify.model.family.tree.FamilyTree;

interface GedcomFamilyTreeBuilder {

    void read(int level, int start, String line);

    FamilyTree build();

}
