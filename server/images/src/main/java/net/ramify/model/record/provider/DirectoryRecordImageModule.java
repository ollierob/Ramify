package net.ramify.model.record.provider;

import com.google.inject.AbstractModule;
import net.ramify.model.record.image.RecordImagesProvider;

public class DirectoryRecordImageModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(RecordImagesProvider.class).to(DirectoryRecordImagesProvider.class);
    }

}
