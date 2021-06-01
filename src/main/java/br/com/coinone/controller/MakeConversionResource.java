package br.com.coinone.controller;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.coinone.dto.OperationDTO;
import io.smallrye.mutiny.Uni;



@Tag(name = "Services")
@Path("/makeConversion")
@RequestScoped
public class MakeConversionResource {

	@Inject
	private ConversionTransationImpl conversionTransationImpl;
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Transactional
	@Operation(description = "service responsible for carrying out currency conversion operations")
	public Uni<Response> doConversion(@RequestBody OperationDTO dto){				
		return conversionTransationImpl.doConversion(dto);
	}


	

}