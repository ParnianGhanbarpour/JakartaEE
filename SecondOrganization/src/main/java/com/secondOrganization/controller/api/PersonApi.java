package com.secondOrganization.controller.api;

import com.secondOrganization.controller.exception.ErrorResponse;
import com.secondOrganization.controller.exception.ResourceNotFoundException;
import com.secondOrganization.dto.PersonDTO;
import com.secondOrganization.model.Mapper.PersonMapper;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.service.PersonService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/api/v1/persons")
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


    @POST
    public Response create(@Valid PersonDTO dto, @Context UriInfo uriInfo) throws Exception {
        Person person = new Person();
        PersonMapper.updateEntityFromDTO(person, dto);
        personService.save(person);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(person.getId()))
                .build();

        return Response.created(location)
                .entity(PersonMapper.toDTO(person))
                .build(); // 201 Created
    }
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PersonDTO dto) throws Exception {
        Optional<Person> personOpt = personService.findById(id);
        if (personOpt.isEmpty()) {
            throw new ResourceNotFoundException("Person", id);
        }

        Person person = personOpt.get();
        PersonMapper.updateEntityFromDTO(person, dto);
        personService.edit(person);

        return Response.ok(PersonMapper.toDTO(person)).build();
    }

    @GET
    public Response findAll() {
        try {
            List<Person> persons = personService.findAll();
            List<PersonDTO> dtos = PersonMapper.toDTOList(persons);
            return Response.ok(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            Optional<Person> person = personService.findById(id);
            if (person.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Person not found"))
                        .build();
            }
            PersonDTO dto = PersonMapper.toDTO(person.get());
            return Response.ok(dto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
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
