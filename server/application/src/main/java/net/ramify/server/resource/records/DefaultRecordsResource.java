package net.ramify.server.resource.records;

import net.ramify.authentication.UserSession;
import net.ramify.model.record.collection.AggregateRecords;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.image.ImageId;
import net.ramify.model.record.image.RecordImage;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.image.RecordImagesProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.server.resource.AbstractResource;
import net.ramify.utils.collections.ListUtils;
import net.ramify.utils.file.FileUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DefaultRecordsResource extends AbstractResource implements RecordsResource {

    private final RecordsProvider records;
    private final RecordSetRelativesProvider relatives;
    private final RecordSetResource recordSets;
    private final IndividualRecordResource individuals;
    private final RecordImagesProvider imageProvider;
    private final PlaceRecordResource placeRecords;

    @Inject
    DefaultRecordsResource(
            final RecordsProvider records,
            final RecordSetRelativesProvider relatives,
            final RecordSetResource recordSets,
            final IndividualRecordResource individuals,
            final RecordImagesProvider imageProvider,
            final PlaceRecordResource placeRecords) {
        this.records = records;
        this.relatives = relatives;
        this.recordSets = recordSets;
        this.individuals = individuals;
        this.imageProvider = imageProvider;
        this.placeRecords = placeRecords;
    }

    @Override
    public Records records(
            final RecordSetId id,
            final boolean includeChildren,
            final int start,
            final int limit) {
        final var records = includeChildren ? this.parentAndChildRecords(id) : this.records.require(id);
        return records.paginate(start, limit);
    }

    private Records parentAndChildRecords(final RecordSetId id) {
        final var parent = records.require(id);
        final var children = relatives.descendants(id).stream().map(RecordSet::recordSetId).map(records::require).collect(Collectors.toList());
        return new AggregateRecords(ListUtils.prefix(parent, children));
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public IndividualRecordResource individuals() {
        return individuals;
    }

    @Override
    public RecordImages images(final RecordSetId id) {
        return imageProvider.get(id);
    }

    @Override
    public Response image(final RecordSetId recordId, final ImageId imageId) {
        return Optional.ofNullable(imageProvider.get(recordId))
                .map(images -> images.image(imageId))
                .map(this::readImage)
                .orElseGet(AbstractResource::notFound);
    }

    private Response readImage(final RecordImage image) {
        return this.readSessionResource(UserSession.INTERNAL, image.path(), mediaType(image));
    }

    private static MediaType mediaType(final RecordImage image) {
        return FileUtils.extension(image.file())
                .map(ext -> new MediaType(IMAGE_MEDIA_TYPE.getType(), ext))
                .orElse(IMAGE_MEDIA_TYPE);
    }

    @Override
    public PlaceRecordResource places() {
        return placeRecords;
    }

}
