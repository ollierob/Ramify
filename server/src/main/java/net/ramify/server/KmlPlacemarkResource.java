package net.ramify.server;

import net.ramify.model.kml.KmlBody;
import net.ramify.model.kml.KmlDocument;
import net.ramify.model.place.KmlPlacemark;
import net.ramify.model.place.RegionId;
import net.ramify.model.place.RegionalAddressProvider;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.Addresses;

import javax.annotation.CheckForNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("kml/locations")
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class KmlPlacemarkResource {

    private final RegionalAddressProvider churches, farms, mills;

    public KmlPlacemarkResource(
            final RegionalAddressProvider churches,
            final RegionalAddressProvider farms,
            final RegionalAddressProvider mills) {
        this.churches = churches;
        this.farms = farms;
        this.mills = mills;
    }

    @GET
    @Path("churches/{region}")
    public KmlBody churches(@PathParam("region") final RegionId region) {
        return convert("churches", churches.get(region));
    }

    @GET
    @Path("farms/{region}")
    public KmlBody farms(@PathParam("region") final RegionId region) {
        return convert("farms", farms.get(region));
    }

    @GET
    @Path("mills/{region}")
    public KmlBody mills(@PathParam("region") final RegionId region) {
        return convert("mills", mills.get(region));
    }

    private static KmlBody convert(final String name, final Addresses locations) {
        final List<KmlPlacemark> placemarks = locations == null
                ? List.of()
                : locations.addresses().stream().map(KmlPlacemarkResource::convert).filter(Objects::nonNull).collect(Collectors.toList());
        return new KmlBody(new KmlDocument(name, placemarks));
    }

    @CheckForNull
    private static KmlPlacemark convert(final Address address) {
        return address.building().map(building -> KmlPlacemark.convert(building, address.describe())).orElse(null);
    }

}
