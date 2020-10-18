package net.ramify.model.place.xml;

import net.ramify.model.place.hierarchy.ParentChildHierarchy;
import org.junit.jupiter.api.Test;

import static net.ramify.model.place.PlaceIdTest.mockPlaceId;
import static org.junit.jupiter.api.Assertions.*;

class XmlPlaceHierarchyProviderTest {

    @Test
    void shouldBuildHierarchy() {

        final var topId = mockPlaceId();
        final var midId = mockPlaceId();
        final var lowId = mockPlaceId();

        final var provider = new XmlPlaceHierarchyProvider();
        provider.add(new ParentChildHierarchy(topId, midId));
        provider.add(new ParentChildHierarchy(midId, lowId));

        final var hierarchies = provider.hierarchiesAbove(lowId);
        assertEquals(1, hierarchies.size());

        var hierachy = hierarchies.iterator().next();
        assertNotNull(hierachy);
        assertEquals(lowId, hierachy.placeId());

        hierachy = hierachy.parent();
        assertNotNull(hierachy);
        assertEquals(midId, hierachy.placeId());

        hierachy = hierachy.parent();
        assertNotNull(hierachy);
        assertEquals(topId, hierachy.placeId());

        assertNull(hierachy.parent());

    }

}