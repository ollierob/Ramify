package net.ramify.server.resource.jaxrs;

import net.ramify.server.resource.Cached;

import javax.annotation.Nonnull;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class CacheControlFilter implements DynamicFeature {

    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext context) {
        if (resourceInfo.getResourceMethod().isAnnotationPresent(Cached.class)) {
            final var cached = resourceInfo.getResourceMethod().getAnnotation(Cached.class);
            context.register(new CacheController(cached.isPrivate() ? "private" : "public", "max-age=" + maxAge(cached), cached.isImmutable() ? "immutable" : null));
        }
    }

    private static int maxAge(@Nonnull final Cached annotation) {
        return annotation.maxAgeSeconds()
                + (60 * annotation.maxAgeMinutes())
                + (60 * 60 * annotation.maxAgeHours())
                + (60 * 60 * 24 * annotation.maxAgeDays());
    }

    private static class CacheController implements ContainerResponseFilter {

        private final List<Object> values;

        private CacheController(final String... values) {
            this.values = Stream.of(values).filter(Objects::nonNull).collect(Collectors.toList());
        }

        @Override
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
            responseContext.getHeaders().putIfAbsent(HttpHeaders.CACHE_CONTROL, values); //Don't override upstream value
        }

    }

}
