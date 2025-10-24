package com.secondOrganization.controller.api;

import com.secondOrganization.model.entity.Person;
import com.secondOrganization.service.PersonService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonApi {

    @Inject
    private PersonService personService;

    @POST
    public Response save(Person person) {
        try {
            personService.save(person);
            return Response.status(Response.Status.CREATED).entity(person).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response edit(Person person) {
        try {
            personService.edit(person);
            return Response.ok(person).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        try {
            personService.removeById(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    public Response findAll() {
        try {
            List<Person> persons = personService.findAll();
            return Response.ok(persons).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Person> person = personService.findById(id);
            return Response.ok(person).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/username/{username}")
    public Response findByUsername(@PathParam("username") String username) {
        try {
            Optional<Person> person = personService.findByUsername(username);
            return person.map(value -> Response.ok(value).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        try {
            return Response.ok(personService.findByName(name)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/family/{family}")
    public Response findByFamily(@PathParam("family") String family) {
        try {
            return Response.ok(personService.findByFamily(family)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/fullname")
    public Response findByNameAndFamily(@QueryParam("name") String name,
                                        @QueryParam("family") String family) {
        try {
            return Response.ok(personService.findByNameAndFamily(name, family)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/nationalCode/{code}")
    public Response findByNationalCode(@PathParam("code") String code) {
        try {
            Optional<Person> person = personService.findByNationalCode(code);
            return person.map(value -> Response.ok(value).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
}
