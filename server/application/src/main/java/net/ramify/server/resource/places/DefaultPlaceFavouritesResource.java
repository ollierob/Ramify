package net.ramify.server.resource.places;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.PlaceIds;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class DefaultPlaceFavouritesResource implements PlaceFavouritesResource {

    private final File file;

    @Inject
    DefaultPlaceFavouritesResource() throws Exception {
        this.file = new File(this.getClass().getResource("/favourites").toURI());
        Preconditions.checkState(file.exists() || file.createNewFile());
    }

    @Override
    public synchronized PlaceIds all() {
        return this.placeIds()::iterator;
    }

    private Set<PlaceId> placeIds() {
        try (final var lines = Files.asCharSource(file, Charset.defaultCharset()).lines()) {
            return lines.map(PlaceId::new).collect(Collectors.toSet());
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public synchronized PlaceIds add(final PlaceId placeId) {
        final var placeIds = this.placeIds();
        if (placeIds.add(placeId)) this.write(placeIds);
        return placeIds::iterator;
    }

    @Override
    public synchronized PlaceIds remove(final PlaceId placeId) {
        final var placeIds = this.placeIds();
        if (placeIds.remove(placeId)) this.write(placeIds);
        return placeIds::iterator;
    }

    private void write(final Set<PlaceId> placeIds) {
        try (final var writer = Files.newWriter(file, Charset.defaultCharset())) {
            for (final var placeId : placeIds) {
                writer.write(placeId.value());
                writer.newLine();
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
