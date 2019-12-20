package net.ramify.model.event.xml;

import com.google.inject.AbstractModule;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class XmlEventModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlHistoricEventModule());
    }

}
