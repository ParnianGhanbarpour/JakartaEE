package com.secondOrganization.controller.api;


import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.impl.OrganizationServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/organization")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationApi {
    @Inject
    private OrganizationServiceImpl organizationService;

    @POST

    public Response save(Organization organization) throws Exception {
        try {
            organizationService.save(organization);
            return Response.ok().entity(organization).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT

    public Response edit(Organization organization) throws Exception {
        try {
            organizationService.edit(organization);
            return Response.ok().entity(organization).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) throws Exception {
        log.info("organization Delete api : " + id);
        try {
            organizationService.removeById(id);
            return Response.ok().entity(id).build();
        } catch (Exception e) {
            return Response.status(204).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    public Response findAll() throws Exception {
        try {
            return Response.ok().entity(organizationService.findAll()).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) throws Exception {
        try {
            return Response.ok().entity(organizationService.findById(id)).build();
        } catch (Exception e) {
            return Response.status(204).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/findByName/{name}")
    public Response findByTitleId(@PathParam("name") String name){
        try {
            return Response.ok().entity(organizationService.findByName(String.valueOf(name))).build();
        } catch (Exception e) {
            return Response.status(204).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
        }
    }

}
