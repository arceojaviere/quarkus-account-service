package org.test.errorhandling;
 
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.test.errorhandling.dto.Status;
import org.test.errorhandling.dto.StatusBean;
 
@ApplicationScoped
@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> 
{

    private Jsonb marshaller = JsonbBuilder.create();

    @Override
    public Response toResponse(ProcessingException exception) 
    {
        StatusBean status = new StatusBean();
        status.setStatus(Status.ERROR);
        status.setDescription("Request processing exception");
        status.setMessage(exception.getMessage());


        return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST).entity(this.marshaller.toJson(status)).build();  

    }
}