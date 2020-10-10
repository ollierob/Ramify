package net.ramify.server.resource.jaxrs;

import net.ramify.authentication.NotLoggedInException;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class NotLoggedInExceptionMapper implements ExceptionMapper<NotLoggedInException> {

    @Override
    public Response toResponse(final NotLoggedInException exception) {
        return Response.status(404).build();
    }

}
