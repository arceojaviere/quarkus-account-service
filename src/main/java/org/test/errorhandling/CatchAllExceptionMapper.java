package org.test.errorhandling;
 
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@ApplicationScoped
@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Throwable> 
{
    @Override
    public Response toResponse(Throwable exception) 
    {
        String template = "{\"status\": \"error\", \"error\": \"unknown\", \"exceptionType\": \"%s\", \"message\": \"%s\"";

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
            String.format(template, exception.getClass().getName().toString(), exception.getMessage())).build();  

    }
}