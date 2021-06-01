package br.com.coinone.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import br.com.coinone.model.Conversion;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(path = "/conversion")
public interface ConversionResource extends  PanacheEntityResource<Conversion,Long> { 

	@MethodProperties(exposed = false)
	public Conversion get(Long id);
	
	@MethodProperties(exposed = false)
	public List<Conversion> list();
	
	@MethodProperties(exposed = false)
	public Response add(Long id);
	
	@MethodProperties(exposed = false)
	public  Conversion update(Long id, Conversion user);
	
	@MethodProperties(exposed = false)
	public  boolean delete(Long id);


	

}
