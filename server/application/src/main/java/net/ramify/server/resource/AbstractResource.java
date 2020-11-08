package net.ramify.server.resource;

import com.google.common.base.Preconditions;
import net.ramify.authentication.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class AbstractResource implements Resource {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Response readRouterResource(final UserSession session, final String bundleKey) {
        return this.readSessionResource(session, "/js/" + bundleKey + ".html", MediaType.TEXT_HTML_TYPE);
    }

    protected Response readSessionResource(final UserSession session, final String resource, final MediaType mediaType) {
        if (!session.permits(this)) {
            logger.warn("Session {} doesn't permit access to {} provided by {}, returning 404", session, resource, this);
            return notFound();
        }
        return this.readOpenResource(resource, mediaType);
    }

    protected static Response notFound() {
        return Response.status(404).build();
    }

    private Response readOpenResource(final String resource, final MediaType mediaType) {
        Preconditions.checkArgument(!resource.contains(".."));
        final var stream = this.getClass().getResourceAsStream(resource);
        if (stream == null) {
            logger.warn("Could not find resource {}", resource);
            return notFound();
        } else {
            return Response.ok(stream).type(mediaType).build();
        }
    }

}
