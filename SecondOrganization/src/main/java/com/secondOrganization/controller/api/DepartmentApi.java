package com.secondOrganization.controller.api;

import com.secondOrganization.model.entity.Department;
import com.secondOrganization.service.DepartmentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class DepartmentApi {

    @Inject
    private DepartmentService departmentService;

    @POST
    public Response save(Department department) {
        try {
            departmentService.save(department);
            return Response.ok().entity(department).build();
        } catch (Exception e) {
            log.error("Error saving department", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    public Response edit(Department department) {
        try {
            departmentService.edit(department);
            return Response.ok().entity(department).build();
        } catch (Exception e) {
            log.error("Error editing department", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        try {
            departmentService.removeById(id);
            return Response.ok().entity("{\"message\": \"Department deleted successfully\"}").build();
        } catch (Exception e) {
            log.error("Error deleting department", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response findAll() {
        try {
            List<Department> departments = departmentService.findAll();
            return Response.ok().entity(departments).build();
        } catch (Exception e) {
            log.error("Error finding all departments", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Department> department = departmentService.findById(id);
            if (department.isPresent()) {
                return Response.ok().entity(department.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Department not found\"}")
                        .build();
            }
        } catch (Exception e) {
            log.error("Error finding department by id", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/title/{title}")
    public Response findByTitle(@PathParam("title") String title) {
        try {
            Optional<Department> department = departmentService.findByTitle(title);
            if (department.isPresent()) {
                return Response.ok().entity(department.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Department not found\"}")
                        .build();
            }
        } catch (Exception e) {
            log.error("Error finding department by title", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}