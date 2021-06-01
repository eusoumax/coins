package br.com.coinone.openapi;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(info = 
@Info(description = "Endpoint set for conversion api between two currencies using up-to-date conversion rates",
title = "Symbols Converters",
version = "0.0.1",
contact = @Contact(name = "Maxwell Miranda Rosa",email = "maxwellcsm@hotmail.com")))
public class App extends Application {
    
}