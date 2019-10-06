package net.ramify.server.data;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.ramify.model.family.xml.XmlFamilyTreeModule;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.match.ComposedNameMatcher;
import net.ramify.model.person.name.match.ForenameSurnameMatcher;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.model.record.provider.DirectoryRecordImageModule;
import net.ramify.model.record.xml.XmlRecordModule;
import net.ramify.strategy.match.name.NameMatch;

public class DataModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlPlaceModule());
        this.install(new XmlRecordModule());
        this.install(new XmlFamilyTreeModule());
        this.install(new DirectoryRecordImageModule());
    }

    @Provides
    @Singleton
    NameMatch<Name> nameMatcher() {
        return new ComposedNameMatcher(new ForenameSurnameMatcher());
    }

}
