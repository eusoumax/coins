package br.com.coinone.controller;


import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.coinone.model.User;

@Tag(name = "Entities Persistence")
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserCustonResource { //interface UserResource extends  PanacheEntityResource<User,Long> {
	 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		return User.listAll();
	}
	
	
	@POST
	@Transactional
	public Response  addUser(@RequestBody User user){		
		User.persist(user);
		return Response.status(Status.CREATED).entity(user).build();
	}
	
    @PUT
    @Path("/{id}")
    @Transactional
    public void updatePerson(@PathParam Long id, User user) {
        User u = User.findById(id);
        if(u == null)
            throw new WebApplicationException(Status.NOT_FOUND);
        u.name  = user.name;
        u.age = user.age;
        User.persist(u);
    }
	
	
	@DELETE
    @Path("/{id}")
	@Transactional
    public void deletePerson(@PathParam Long id) {
		User user = User.findById(id);
        if(user == null)
            throw new WebApplicationException(Status.NOT_FOUND);
        User.deleteById(id);
    }
}
