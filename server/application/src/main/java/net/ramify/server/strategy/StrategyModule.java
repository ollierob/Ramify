package net.ramify.server.strategy;

import com.google.inject.AbstractModule;

public class StrategyModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new NameStrategyModule());
        this.install(new EventStrategyModule());
    }

}
