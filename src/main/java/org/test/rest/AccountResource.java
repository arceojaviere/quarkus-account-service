package org.test.rest;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.test.model.domain.Account;
import org.test.model.domain.Person;
import org.test.model.repository.AccountRepository;
import org.test.rest.client.PersonServiceClient;


@Path("/account")
public class AccountResource {

    @Inject
    private AccountRepository repository;

    @Inject
    @RestClient
    private PersonServiceClient personServiceClient;



    @Operation(
        description = "Get a list of all accounts in the database"
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200",
                    description = "A list of Accounts and the people associated with each account",
                    content = @Content(schema = @Schema(implementation = Account.class))
                    ),
            @APIResponse(
                responseCode = "500",
                description = "An unknown error occurred"
            ),
            @APIResponse(
                responseCode = "400",
                description = "The request could not be processed because of the specified inputs. The cause could be a validation issue or other problem with the data"
            )
            
        }
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccounts(  @Parameter(
            description = "A parameter indicating wheter the details of the person associated with the account should be queried or not",
            required = false
            ) @QueryParam("queryPersonDetails") @DefaultValue("false") boolean queryPersonDetails) {
                
        List<Account> accounts = this.repository.findAllAccounts();

        if(queryPersonDetails){
            HashMap<String, Person> people = new HashMap<>();

            accounts.forEach(acc -> people.put(acc.getPerson().getId(), acc.getPerson()));

            for(Person p : people.values()){
                people.put(p.getId(), this.personServiceClient.getPersonById(p.getId()));
            }

            accounts.forEach(acc -> acc.setPerson(people.get(
                acc.getPerson().getId()
                )));
        }

        return accounts;
    }


    @Operation(
        description = "Get the account wih the specified user ID"
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200",
                    description = "The account matching the povided User ID, or an empty document if nothing matches",
                    content = @Content(schema = @Schema(implementation = Account.class))
                    ),
            @APIResponse(
                responseCode = "500",
                description = "An unknown error occurred"
            ),
            @APIResponse(
                responseCode = "400",
                description = "The request could not be processed because of the specified inputs. The cause could be a validation issue or other problem with the data"
            )
            
        }
    )
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccountByUserId( @Parameter(
                description = "The User ID of the account to be recovered", required = true 
            ) @PathParam("userId") String userId,
            @Parameter(
                description = "A parameter indicating wheter the details of the person associated with the account should be queried or not",
                required = false
            ) @QueryParam("queryPersonDetails") @DefaultValue("false") boolean queryPersonDetails) {
                
        Account account = this.repository.findByUserId(userId);

        if(queryPersonDetails){

            account.setPerson(this.personServiceClient.getPersonById(account.getPerson().getId()));
        }

        return account;
    }


    @Operation(
        description = "Add a new account to the database. The person associated with it must exist and be valid"
    )
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200",
                    description = "The new account generated",
                    content = @Content(schema = @Schema(implementation = Account.class))
                    ),
            @APIResponse(
                responseCode = "500",
                description = "An unknown error occurred"
            ),
            @APIResponse(
                responseCode = "400",
                description = "The request could not be processed because of the specified inputs. The cause could be a validation issue or other problem with the data"
            )
            
        }
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Account newAccount(
            @Parameter(description = "The account to be added. The account ID, User ID, and Person ID are required", 
                    required = true, name = "account") 
                @Valid Account account){

        return this.repository.persistAndReturn(account);
    }
    
}