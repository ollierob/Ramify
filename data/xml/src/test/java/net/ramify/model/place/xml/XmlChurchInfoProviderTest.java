package net.ramify.model.place.xml;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.church.XmlChurchInfos;
import net.ramify.model.record.provider.RecordSetProvider;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.validation.SchemaFactory;
import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class XmlChurchInfoProviderTest {

    @Test
    void shouldReadRecords() throws Exception {

        final var context = JAXBContext.newInstance(XmlChurchInfos.class);
        final var data = new File(XmlPlaceProviderTest.class.getResource("/xml/data/england/west_yorkshire/morley/heptonstall").toURI());
        final var mockDateParser = mock(DateParser.class);

        final var mockPlaces = mock(PlaceProvider.class);
        when(mockPlaces.require(any(PlaceId.class), eq(Church.class))).thenAnswer(i -> {
            final var church = mock(Church.class);
            when(church.placeId()).thenReturn(i.getArgument(0));
            return church;
        });

        final var mockRecords = mock(RecordSetProvider.class);

        final var schemaFile = new File(XmlPlaceProviderTest.class.getResource("/xml/schema/places.xsd").toURI());
        final var schema = SchemaFactory.newDefaultInstance().newSchema(schemaFile);

        final var unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);

        final var provider = XmlChurchInfoProvider.readChurchInfo(unmarshaller, data, mockPlaces, mockRecords, mockDateParser);

    }

}