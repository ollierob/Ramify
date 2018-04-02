package net.ramify.server;

import net.ramify.model.kml.KmlBody;
import net.ramify.model.kml.KmlDocument;
import net.ramify.model.place.Building;
import net.ramify.model.place.Church;
import net.ramify.model.place.Graveyard;
import net.ramify.model.place.KmlPlacemark;
import net.ramify.model.place.Location;
import net.ramify.model.place.RegionId;
import net.ramify.model.place.RegionalAddressProvider;
import net.ramify.model.place.address.Addresses;
import net.ramify.model.place.position.Position;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("kml/locations")
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class KmlPlacemarkResource {

    private final RegionalAddressProvider churches, graveyards, farms, mills;

    public KmlPlacemarkResource(
            final RegionalAddressProvider churches,
            final RegionalAddressProvider graveyards,
            final RegionalAddressProvider farms,
            final RegionalAddressProvider mills) {
        this.churches = churches;
        this.graveyards = graveyards;
        this.farms = farms;
        this.mills = mills;
    }

    private static KmlBody convert(final String name, @Nonnull final Addresses addresses, final Class<? extends Location> type) {
        final List<KmlPlacemark> placemarks = addresses.addresses()
                .stream()
                .map(address -> convert(address.find(type), address.position(), address.describe()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new KmlBody(new KmlDocument(name, placemarks));
    }

    private static KmlPlacemark convert(final Optional<? extends Location> location, final Optional<? extends Position> position, final Map<String, String> description) {
        return location.isPresent() && position.isPresent()
                ? KmlPlacemark.convert(location.get(), position.get(), description)
                : null;
    }

    @GET
    @Path("{region}/churches")
    public KmlBody churches(@PathParam("region") final RegionId region) {
        return convert("churches", churches.require(region), Church.class);
    }

    @GET
    @Path("{region}/graveyards")
    public KmlBody graveyards(@PathParam("region") final RegionId region) {
        return convert("graveyards", graveyards.require(region), Graveyard.class);
    }

    @GET
    @Path("{region}/farms")
    public KmlBody farms(@PathParam("region") final RegionId region) {
        return convert("farms", farms.require(region), Building.class);
    }

    @GET
    @Path("{region}/mills")
    public KmlBody mills(@PathParam("region") final RegionId region) {
        return convert("mills", mills.require(region), Building.class);
    }

}
