package net.ramify.server;

import com.google.inject.AbstractModule;
import net.ramify.server.authentication.ServerAuthenticationModule;
import net.ramify.server.data.DataModule;
import net.ramify.server.resource.ResourceModule;

public class ProgramModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new ServerAuthenticationModule());
        this.install(new DataModule());
        this.install(new ResourceModule());
    }
}
