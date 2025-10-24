package com.secondOrganization.controller.api;

import com.secondOrganization.model.entity.OrganizationGroup;
import com.secondOrganization.service.OrganizationGroupService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequestScoped
@Path("/organization-groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class OrganizationGroupApi {

    @Inject
    private OrganizationGroupService organizationGroupService;

    @POST
    public Response save(OrganizationGroup group) {
        try {
            organizationGroupService.save(group);
            return Response.ok().entity(group).build();
        } catch (Exception e) {
            log.error("Error saving organization group", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    public Response edit(OrganizationGroup group) {
        try {
            organizationGroupService.edit(group);
            return Response.ok().entity(group).build();
        } catch (Exception e) {
            log.error("Error editing organization group", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        try {
            organizationGroupService.removeById(id);
            return Response.ok().entity("{\"message\": \"Organization group deleted successfully\"}").build();
        } catch (Exception e) {
            log.error("Error deleting organization group", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response findAll() {
        try {
            List<OrganizationGroup> groups = organizationGroupService.findAll();
            return Response.ok().entity(groups).build();
        } catch (Exception e) {
            log.error("Error finding all organization groups", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            return organizationGroupService.findById(id)
                    .map(group -> Response.ok().entity(group).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Organization group not found\"}")
                            .build());
        } catch (Exception e) {
            log.error("Error finding organization group by id", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/department/{departmentId}")
    public Response findByDepartmentId(@PathParam("departmentId") Long departmentId) {
        try {
            List<OrganizationGroup> groups = organizationGroupService.findByDepartmentId(departmentId);
            return Response.ok().entity(groups).build();
        } catch (Exception e) {
            log.error("Error finding organization groups by department", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        try {
            List<OrganizationGroup> groups = organizationGroupService.findByName(name);
            return Response.ok().entity(groups).build();
        } catch (Exception e) {
            log.error("Error finding organization groups by name", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}