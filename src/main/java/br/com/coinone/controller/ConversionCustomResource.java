package br.com.coinone.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.coinone.model.Conversion;

@Tag(name = "Entities Persistence")
@Path("/conversion")
public class ConversionCustomResource {
   
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Conversions histories those were had done")
	public List<Conversion> getConversion(){
		return Conversion.listAll();
	}
    
}
