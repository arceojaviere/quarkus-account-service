package org.test.errorhandling;
 
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.test.errorhandling.dto.Status;
import org.test.errorhandling.dto.StatusBean;
 
@ApplicationScoped
@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Throwable> 
{

    private static final Logger LOGGER = Logger.getLogger(CatchAllExceptionMapper.class);

    private Jsonb marshaller = JsonbBuilder.create();

    @Override
    public Response toResponse(Throwable exception) 
    {
        StatusBean status = new StatusBean();
        status.setStatus(Status.ERROR);
        status.setDescription("Unknown Error");
        status.setExceptionType(exception.getClass().getName().toString());
        status.setMessage(exception.getMessage());
        
        if(CatchAllExceptionMapper.LOGGER.isDebugEnabled()){
            exception.printStackTrace(System.err);
        }

        return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(this.marshaller.toJson(status)).build();  

    }
}