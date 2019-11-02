package net.ramify.model.family.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeProvider;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class XmlFamilyTreeProvider extends AbstractMappedProvider<FamilyTreeId, FamilyTree> implements FamilyTreeProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlFamilyTreeProvider.class);

    XmlFamilyTreeProvider(final Map<FamilyTreeId, FamilyTree> map) {
        super(map);
    }

    FamilyTreeProvider immutable() {
        return new XmlFamilyTreeProvider(this.immutableMap());
    }

    void add(final FamilyTree familyTree) {
        this.put(familyTree.familyTreeId(), familyTree);
    }

    @Nonnull
    @Override
    public Set<FamilyTreeId> allIds() {
        return this.keys();
    }

    static FamilyTreeProvider readTreesInDirectory(
            final JAXBContext jaxbContext,
            final File root,
            final RecordContext context) throws JAXBException {
        final var unmarshaller = jaxbContext.createUnmarshaller();
        final var provider = new XmlFamilyTreeProvider(Maps.newHashMap());
        FileTraverseUtils.traverseSubdirectories(
                root,
                file -> file.getName().endsWith(".xml"),
                file -> readRecordsInFile(unmarshaller, file, provider, context));
        return provider.immutable();
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final XmlFamilyTreeProvider provider, final RecordContext context) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading families from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlFamilyTree)) return;
            final var familyTree = ((XmlFamilyTree) unmarshalled).toFamilyTree(context);
            provider.add(familyTree);
        } catch (final JAXBException jex) {
            logger.warn("Could not read families in file " + file + ": " + jex.getMessage());
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading families in file " + file, ex);
        }
    }

}
