package net.ramify.model.family;

import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.MappedFamilyTreeProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class GedcomFamilyTreeProvider extends MappedFamilyTreeProvider {

    private final GedcomFamilyTreeFactory factory;

    @Inject
    public GedcomFamilyTreeProvider(final GedcomFamilyTreeFactory factory) {
        this.factory = factory;
    }

    @Override
    protected FamilyTree create(final FamilyTreeId id) {
        try {
            final var file = this.fileFor(id);
            return file == null ? null : factory.create(id, file);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private File fileFor(final FamilyTreeId id) {
        throw new UnsupportedOperationException();
    }

}
