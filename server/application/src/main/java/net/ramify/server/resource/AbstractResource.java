package net.ramify.server.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.authentication.UserSession;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractResource implements Resource {

    private static final String JSON_MANIFEST = "/js/build-manifest.json";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Map<String, String> bundleCache = Maps.newConcurrentMap();

    protected Response readRouterResource(final UserSession session, final String bundleKey) {
        final var replacements = Collections.singletonMap("<!--%BUNDLEs%-->", bundleCache.computeIfAbsent(bundleKey, this::readBundles));
        return this.readSessionResource(session, "/router.html", replacements);
    }

    protected Response readSessionResource(final UserSession session, final String resource) {
        return this.readSessionResource(session, resource, Collections.emptyMap());
    }

    protected Response readSessionResource(final UserSession session, final String resource, final Map<String, String> replacements) {
        if (!session.permits(this)) {
            logger.warn("Session {} doesn't permit access to {} provided by {}, returning 404", session, resource, this);
            return notFound();
        }
        return this.readOpenResource(resource, replacements);
    }

    private static Response notFound() {
        return Response.status(404).build();
    }

    private Response readOpenResource(final String resource, final Map<String, String> replacements) {
        Preconditions.checkArgument(!resource.contains(".."));
        final var stream = this.getClass().getResourceAsStream(resource);
        if (stream == null) {
            logger.warn("Could not find resource {}", resource);
            return notFound();
        } else {
            return Response.ok(performStreamReplacements(stream, replacements)).type(MediaType.TEXT_HTML).build();
        }
    }

    private String readBundles(final String manifestKey) {
        try (final var manifest = Objects.requireNonNull(this.getClass().getResourceAsStream(JSON_MANIFEST), "Could not open TS manifest file (was there a build error?)")) {
            final var jsBundleFiles = Sets.<String>newHashSetWithExpectedSize(10);
            final var mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            final Map<String, Bundle> map = mapper.readValue(manifest, Bundle.TYPE_REFERENCE);
            Maps.filterKeys(map, key -> key.equals("vendors") || key.contains(manifestKey)).forEach((k, bundle) -> jsBundleFiles.addAll(bundle.jsBundleFiles));
            return jsBundleFiles.stream().map(AbstractResource::scriptTag).collect(Collectors.joining("\n"));
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String scriptTag(final String bundleJs) {
        return "<script src=\"/js/" + bundleJs + "\"></script>";
    }

    private static InputStream performStreamReplacements(final InputStream in, final Map<String, String> replacements) {
        if (replacements.isEmpty()) {
            return in;
        }
        try {
            final var out = IOUtils.toString(in, Charset.defaultCharset());
            final var replaced = replacements.entrySet().stream().reduce(out, (current, replace) -> current.replace(replace.getKey(), replace.getValue()), String::concat);
            return IOUtils.toInputStream(replaced, Charset.defaultCharset());
        } catch (final IOException iex) {
            //Shouldn't happen
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
