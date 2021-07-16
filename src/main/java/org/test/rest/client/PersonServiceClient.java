package org.test.rest.client;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.test.model.domain.Person;

@Path("/person")
@RegisterRestClient(configKey = "personServiceClient")
public interface PersonServiceClient {

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") String id);

   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person newPerson(Person Person);
    
}