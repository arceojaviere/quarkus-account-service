package org.test.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import java.sql.SQLException;
import javax.sql.DataSource;

@ApplicationScoped
@Liveness
public class DatabaseLivenessProbe implements HealthCheck{
    
    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call(){
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Account service database liveness check");
        try{

            dataSource.getConnection().close();
            
            responseBuilder.up();
        }catch(SQLException sqle){
            responseBuilder.down()
            .withData("error", "database error")
            .withData("errorMessage", sqle.getMessage());
        }catch(Throwable t){
            responseBuilder.down()
            .withData("error", "unknown error")
            .withData("errorMessage", t.getMessage());
        }

        return responseBuilder.build();
    }
    
}
