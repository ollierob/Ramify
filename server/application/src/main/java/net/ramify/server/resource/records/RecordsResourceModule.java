package net.ramify.server.resource.records;

import com.google.inject.AbstractModule;

public class RecordsResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(RecordsRouterResource.class);
        this.bind(DefaultRecordsResource.class);
        this.bind(RecordSetResource.class).to(DefaultRecordSetResource.class);
    }

}
