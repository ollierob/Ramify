package net.ramify.server.authentication;

import com.google.inject.AbstractModule;
import net.ramify.authentication.AuthenticationModule;

public class ServerAuthenticationModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new AuthenticationModule());
        this.bind(UserSessionInterceptorFeature.class);
    }

}
