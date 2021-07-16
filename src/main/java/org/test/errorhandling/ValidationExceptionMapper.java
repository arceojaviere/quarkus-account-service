package org.test.errorhandling;
 
import javax.enterprise.context.ApplicationScoped;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@ApplicationScoped
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> 
{
    @Override
    public Response toResponse(ValidationException exception) 
    {
        String template = "{\"status\": \"error\", \"error\": \"Validation error\", \"message\": \"%s\"";

        return Response.status(Status.BAD_REQUEST).entity(
            String.format(template, exception.getMessage())).build();  

    }
}