package net.ramify.server;

import com.google.inject.Injector;
import org.eclipse.jetty.server.ResourceService;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import java.util.EnumSet;

public class Server {

    private final Injector injector;

    public Server(final Injector injector) {
        this.injector = injector;
    }

    public void run() throws Exception {

        final var server = new org.eclipse.jetty.server.Server();

        final var servletHandler = new ServletContextHandler();
        servletHandler.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false"); //Disable directory listings
        servletHandler.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));
        servletHandler.addServlet(new ServletHolder(this.defaultServlet()), "/*");
        servletHandler.addFilter(new FilterHolder(FilterDispatcher.class), "/*", EnumSet.of(DispatcherType.REQUEST));

        final var resourceBasePath = Server.class.getResource("/webroot").toExternalForm();
        servletHandler.setResourceBase(resourceBasePath);

        final var gzipHandler = new GzipHandler();
        gzipHandler.setHandler(servletHandler);
        server.setHandler(gzipHandler);

        server.start();
        server.join();

    }

    private Servlet defaultServlet() {
        final var resourceService = new ResourceService();
        resourceService.setEtags(true);
        return new DefaultServlet(resourceService);
    }

}
