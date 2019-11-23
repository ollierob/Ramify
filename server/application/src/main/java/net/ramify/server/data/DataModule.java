package net.ramify.server.data;

import com.google.inject.AbstractModule;
import net.ramify.model.family.xml.XmlFamilyTreeModule;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.provider.DirectoryRecordImageModule;
import net.ramify.model.record.xml.XmlRecordModule;
import net.ramify.server.data.merge.MergeStrategyModule;

public class DataModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlPlaceModule());
        this.install(new XmlRecordModule());
        this.install(new XmlFamilyTreeModule());
        this.install(new DirectoryRecordImageModule());
        this.install(new MergeStrategyModule());
    }

}
