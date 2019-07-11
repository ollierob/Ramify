package net.ramify.server.data;

import com.google.inject.AbstractModule;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.xml.XmlRecordModule;

public class DataModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlPlaceModule());
        this.install(new XmlRecordModule());
    }
}
