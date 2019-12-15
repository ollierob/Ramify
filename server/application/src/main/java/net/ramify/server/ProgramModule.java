package net.ramify.server;

import com.google.inject.AbstractModule;
import net.ramify.server.authentication.ServerAuthenticationModule;
import net.ramify.server.data.DataModule;
import net.ramify.server.resource.ResourceModule;
import net.ramify.server.strategy.StrategyModule;

public class ProgramModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new ServerAuthenticationModule());
        this.install(new DataModule());
        this.install(new StrategyModule());
        this.install(new ResourceModule());
    }

}
