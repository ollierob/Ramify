package net.ramify.model;

import net.ramify.model.person.provider.PersonProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.archive.ArchiveProvider;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.Nonnull;

public interface Providers {

    @Nonnull
    ArchiveProvider archives();

    @Nonnull
    PlaceProvider places();

    @Nonnull
    PersonProvider people();

    @Nonnull
    RecordSetProvider recordSets();

}
