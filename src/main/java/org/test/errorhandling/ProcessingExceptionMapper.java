package org.test.errorhandling;
 
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@ApplicationScoped
@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> 
{
    @Override
    public Response toResponse(ProcessingException exception) 
    {
        String template = "{\"status\": \"error\", \"error\": \"Request processing exception\", \"message\": \"%s\"";

        return Response.status(Status.BAD_REQUEST).entity(
            String.format(template, exception.getMessage())).build();  

    }
}