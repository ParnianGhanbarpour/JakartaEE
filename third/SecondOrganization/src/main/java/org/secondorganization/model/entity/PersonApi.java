package org.secondorganization.model.entity;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;

@Path("/persons")
@Consumes("application/json")
@Produces("application/json")
public class PersonApi {
    @Inject
    Person person;

    @GET
    public Person getPerson(){
//                service.save();
        person.setName("ali");
        return person;
    }

    @POST
    public void savePerson(@Valid Person person){
        System.out.println(person.getName()+ " " + person.getFamily());
    }
}
