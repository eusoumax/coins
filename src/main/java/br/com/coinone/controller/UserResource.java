package br.com.coinone.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import br.com.coinone.model.User;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(path = "/user")
public interface UserResource extends  PanacheEntityResource<User,Long> { 


	@MethodProperties(exposed = false)
	public User get(Long id);
	
	@MethodProperties(exposed = false)
	public List<User> list();
	
	@MethodProperties(exposed = false)
	public Response add(Long id);
	
	@MethodProperties(exposed = false)
	public  User update(Long id, User user);
	
	@MethodProperties(exposed = false)
	public  boolean delete(Long id);
	

}
