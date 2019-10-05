package net.ramify.server.resource.places;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.provider.PlaceProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class DefaultPlaceFavouritesResource implements PlaceFavouritesResource {

    private final PlaceProvider placeProvider;
    private final File file;

    @Inject
    DefaultPlaceFavouritesResource(final PlaceProvider placeProvider) throws Exception {
        this.placeProvider = placeProvider;
        this.file = new File(this.getClass().getResource("/favourites").toURI());
        Preconditions.checkState(file.exists() || file.createNewFile());
    }

    @Override
    public synchronized Places all() {
        return this.places(this.placeIds());
    }

    private Places places(final Set<PlaceId> placeIds) {
        final var places = placeIds.stream().map(placeProvider::get).filter(Objects::nonNull).collect(Collectors.toList());
        return places::iterator;
    }

    private Set<PlaceId> placeIds() {
        try (final var lines = Files.asCharSource(file, Charset.defaultCharset()).lines()) {
            return lines.map(PlaceId::new).collect(Collectors.toSet());
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public synchronized Places add(final PlaceId placeId) {
        final var placeIds = this.placeIds();
        if (placeIds.add(placeId)) this.write(placeIds);
        return this.places(placeIds);
    }

    @Override
    public synchronized Places remove(final PlaceId placeId) {
        final var placeIds = this.placeIds();
        if (placeIds.remove(placeId)) this.write(placeIds);
        return this.places(placeIds);
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
