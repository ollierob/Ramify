package net.ramify.server;

import com.google.inject.Guice;

public class Program {

    public static void main(final String[] args) throws Exception {

        final var injector = Guice.createInjector();

        new Server(injector).run();

    }

}
