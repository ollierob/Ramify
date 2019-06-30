package net.ramify.server.authentication;

import net.ramify.authentication.UserSessionContext;
import org.jboss.resteasy.core.ResteasyContext;

import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Singleton
@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class UserSessionInterceptorFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        featureContext.register(new InsecureContainerFilter(resourceInfo));
    }

    private class InsecureContainerFilter implements ContainerRequestFilter {

        private final ResourceInfo resourceInfo;

        private InsecureContainerFilter(final ResourceInfo resourceInfo) {
            this.resourceInfo = resourceInfo;
        }

        @Override
        public void filter(final ContainerRequestContext requestContext) {
            ResteasyContext.pushContext(UserSessionContext.class, new StubSessionContext());
        }

    }

}
