package net.ramify.server;

import net.ramify.model.kml.KmlBody;
import net.ramify.model.kml.KmlDocument;
import net.ramify.model.place.KmlPlacemark;
import net.ramify.model.place.LocationProvider;
import net.ramify.model.place.Locations;
import net.ramify.model.place.RegionId;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("kml")
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class KmlResource {

    private final LocationProvider churches, farms, mills;

    public KmlResource(
            final LocationProvider churches,
            final LocationProvider farms,
            final LocationProvider mills) {
        this.churches = churches;
        this.farms = farms;
        this.mills = mills;
    }

    private static KmlBody convert(final String name, final Locations locations) {
        final List<KmlPlacemark> placemarks = locations == null
                ? List.of()
                : locations.locations().stream().map(KmlPlacemark::convert).collect(Collectors.toList());
        return new KmlBody(new KmlDocument(name, placemarks));
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

}
