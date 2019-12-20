package net.ramify.server.data;

import com.google.inject.AbstractModule;
import net.ramify.model.date.XmlDateParser;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.event.xml.XmlEventModule;
import net.ramify.model.family.xml.XmlFamilyTreeModule;
import net.ramify.model.person.XmlNameParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.provider.DirectoryRecordImageModule;
import net.ramify.model.record.xml.XmlRecordModule;

public class DataModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(DateParser.class).to(XmlDateParser.class);
        this.bind(NameParser.class).to(XmlNameParser.class);
        this.install(new XmlPlaceModule());
        this.install(new XmlRecordModule());
        this.install(new XmlFamilyTreeModule());
        this.install(new XmlEventModule());
        this.install(new DirectoryRecordImageModule());
    }

}
