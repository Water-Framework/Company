package it.water.company.api.rest;

import it.water.company.model.*;
import it.water.core.api.model.PaginableResult;
import it.water.core.api.service.rest.FrameworkRestApi;
import it.water.core.api.service.rest.RestApi;
import it.water.service.rest.api.security.LoggedIn;

import com.fasterxml.jackson.annotation.JsonView;
import it.water.core.api.service.rest.WaterJsonView;
import io.swagger.annotations.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Generated by Water Generator
 * Rest Api Interface for Company entity.
 * This interfaces exposes all CRUD methods with default JAXRS annotations.
 *
 */
@Path("/companies")
@Api(produces = MediaType.APPLICATION_JSON, tags = "Company API")
@FrameworkRestApi
public interface CompanyRestApi extends RestApi {

   
       
    @LoggedIn
        @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    @ApiOperation(value = "/", notes = "Company Save API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 409, message = "Validation Failed"),
            @ApiResponse(code = 422, message = "Duplicated Entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    Company save(Company company);

       
    @LoggedIn
        @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    @ApiOperation(value = "/", notes = "Company Update API", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 409, message = "Validation Failed"),
            @ApiResponse(code = 422, message = "Duplicated Entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    Company update(Company company);

       
    @LoggedIn
        @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    @ApiOperation(value = "/{id}", notes = "Company Find API", httpMethod = "GET", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 409, message = "Validation Failed"),
            @ApiResponse(code = 422, message = "Duplicated Entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    Company find(@PathParam("id") long id);

       
    @LoggedIn
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    @ApiOperation(value = "/", notes = "Company Find All API", httpMethod = "GET", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 409, message = "Validation Failed"),
            @ApiResponse(code = 422, message = "Duplicated Entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    PaginableResult<Company> findAll();

       
    @LoggedIn
        @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(WaterJsonView.Public.class)
    @ApiOperation(value = "/{id}", notes = "Company Delete API", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 409, message = "Validation Failed"),
            @ApiResponse(code = 422, message = "Duplicated Entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    void remove(@PathParam("id") long id);
}
