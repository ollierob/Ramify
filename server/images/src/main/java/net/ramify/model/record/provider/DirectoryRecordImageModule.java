package net.ramify.model.record.provider;

import com.google.inject.AbstractModule;

public class DirectoryRecordImageModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(RecordImageProvider.class).to(DirectoryRecordImageProvider.class);
    }

}
