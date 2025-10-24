package com.secondOrganization.controller.api;

import com.secondOrganization.model.entity.Role;
import com.secondOrganization.service.RoleService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class RoleApi {

    @Inject
    private RoleService roleService;

    @POST
    public Response save(Role role) {
        try {
            roleService.save(role);
            return Response.ok().entity(role).build();
        } catch (Exception e) {
            log.error("Error saving role", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    public Response edit(Role role) {
        try {
            roleService.edit(role);
            return Response.ok().entity(role).build();
        } catch (Exception e) {
            log.error("Error editing role", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        try {
            roleService.removeById(id);
            return Response.ok().entity("{\"message\": \"Role deleted successfully\"}").build();
        } catch (Exception e) {
            log.error("Error deleting role", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response findAll() {
        try {
            List<Role> roles = roleService.findAll();
            return Response.ok().entity(roles).build();
        } catch (Exception e) {
            log.error("Error finding all roles", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Role> role = roleService.findById(id);
            if (role.isPresent()) {
                return Response.ok().entity(role.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Role not found\"}")
                        .build();
            }
        } catch (Exception e) {
            log.error("Error finding role by id", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/role-name/{roleName}")
    public Response findByRoleName(@PathParam("roleName") String roleName) {
        try {
            List<Role> roles = roleService.findByRoleName(roleName);
            return Response.ok().entity(roles).build();
        } catch (Exception e) {
            log.error("Error finding roles by role name", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/user/{username}")
    public Response findByUser(@PathParam("username") String username) {
        try {
            List<Role> roles = roleService.findByUser(username);
            return Response.ok().entity(roles).build();
        } catch (Exception e) {
            log.error("Error finding roles by user", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/user/{username}/role/{roleName}")
    public Response findByUsernameAndRoleName(@PathParam("username") String username,
                                              @PathParam("roleName") String roleName) {
        try {
            List<Role> roles = roleService.findByUsernameAndRoleName(username, roleName);
            return Response.ok().entity(roles).build();
        } catch (Exception e) {
            log.error("Error finding roles by username and role name", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}