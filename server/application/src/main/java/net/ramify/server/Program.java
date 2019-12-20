package net.ramify.server;

import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(final String[] args) throws Exception {
        try {
            final var injector = Guice.createInjector(new ProgramModule());
            new Server(injector).run();
        } catch (final Exception ex) {
            logger.error("Error initializing", ex);
            throw ex;
        }
    }

}
