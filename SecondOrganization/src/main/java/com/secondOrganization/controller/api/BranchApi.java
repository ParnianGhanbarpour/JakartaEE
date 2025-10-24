package com.secondOrganization.controller.api;

import com.secondOrganization.model.entity.Branch;

import com.secondOrganization.service.impl.BranchServiceImpl;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/branch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class BranchApi {

    @Inject
    private BranchServiceImpl branchService;

    @POST
    public Response save(Branch branch) {
        try {
            branchService.save(branch);
            return Response.ok().entity(branch).build();
        } catch (Exception e) {
            log.error("Error saving branch", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    public Response edit(Branch branch) {
        try {
            branchService.edit(branch);
            return Response.ok().entity(branch).build();
        } catch (Exception e) {
            log.error("Error editing branch", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        log.info("branch Delete api : {}", id);
        try {
            branchService.removeById(id);
            return Response.ok().entity(id).build();
        } catch (Exception e) {
            log.error("Error deleting branch", e);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response findAll() {
        try {
            return Response.ok().entity(branchService.findAll()).build();
        } catch (Exception e) {
            log.error("Error finding all branches", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        try {
            Optional<Branch> branch = branchService.findById(id);
            if (branch.isPresent()) {
                return Response.ok().entity(branch.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Branch not found\"}")
                        .build();
            }
        } catch (Exception e) {
            log.error("Error finding branch by id", e);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/city/{city}")
    public Response findByCity(@PathParam("city") String city) {
        try {
            return Response.ok().entity(branchService.findByCity(city)).build();
        } catch (Exception e) {
            log.error("Error finding branches by city", e);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/organization/{orgId}")
    public Response findByOrganizationId(@PathParam("orgId") Integer orgId) {
        try {
            return Response.ok().entity(branchService.findByOrganizationId(orgId)).build();
        } catch (Exception e) {
            log.error("Error finding branches by organization", e);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}