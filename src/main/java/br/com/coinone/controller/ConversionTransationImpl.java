package br.com.coinone.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.logging.Logger;

import br.com.coinone.dto.OperationDTO;
import br.com.coinone.dto.OperationResponseDTO;
import br.com.coinone.dto.OperationResultDTO;
import br.com.coinone.model.Conversion;
import br.com.coinone.model.User;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
@RequestScoped
@Path("/teste")
public class ConversionTransationImpl {


    private static final Logger logger = Logger.getLogger(MakeConversionResource.class);

    @Inject
	private Vertx vertx;

	@ConfigProperty(name = "quarkus.coinlayer.url")
    String URL_COINLAYER_API;

    @ConfigProperty(name = "quarkus.coinlayer.key")
    String KEY_COIN_LAYER_API;

	@ConfigProperty(name = "quarkus.app.timezone")
	String TIME_ZONE;

    @ConfigProperty(name = "quarkus.app.list.currencies.allowed")
    String LIST_CURRENCIES_ALLOWED;
	
    private List<String> listCurrenciessAllowed = new ArrayList<>();
	
	private WebClient client;



	
	@PostConstruct
	void initialize() {
		this.client = WebClient.create(vertx,
				new WebClientOptions().setDefaultHost(this.URL_COINLAYER_API).setSsl(false)
						.setTrustAll(true));

		//timezone config						
		if(this.TIME_ZONE !=null){
			logger.debug(String.format("Loading Timezone [%s] from quarkus.app.timezone", TIME_ZONE));
			System.setProperty("user.timezone", this.TIME_ZONE);	
		}

        if(LIST_CURRENCIES_ALLOWED !=null){
            logger.debug(String.format("Loading currencies allowed [%s] from quarkus.app.list.currencies.allowed", LIST_CURRENCIES_ALLOWED));

            for(String currency:LIST_CURRENCIES_ALLOWED.split(",")){
                listCurrenciessAllowed.add(currency.trim());
            }
            
        }

		
	}

 
    private User findUser(OperationDTO operationDTO) throws Exception{		

	
		
        User user = User.findById(operationDTO.getIdUser());
        if(user!=null){
            logger.error(String.format("user [%s] found - [%s]", operationDTO.getIdUser(),JsonObject.mapFrom(user).toString()));	
            return user;
        }else{
            String msg = String.format("user [%s] not found", operationDTO.getIdUser());
            logger.error(msg);	
            
            throw new Exception(msg);
        }

	}
    private Response persistConversion(OperationDTO operationDTO,OperationResultDTO operationResultDTO,User user) throws Exception{		

		
		logger.debug("Start conversion transation");
		
		
		try {
            logger.error(String.format("user [%s] found - [%s]", operationDTO.getIdUser(),JsonObject.mapFrom(user).toString()));	
            Conversion conversion = new Conversion(operationDTO, user);
            conversion.setUtcDate(Calendar.getInstance());
            
            String key = getKeyCurrency(operationDTO);
            Double valueTarget = 0D;
            try{
                valueTarget = operationResultDTO.getQuotes().get(key);
                logger.debug(String.format("valueTarget of [%s] -> [%s]",operationDTO.getCurrencyTarget(),valueTarget));
            }catch(Exception e){
                throw new Exception("Problems recovering currency value",e);
            }
            conversion.setValueTarget(valueTarget);
            //conversion.persist();

            OperationResponseDTO responseDTO = new OperationResponseDTO(conversion);
            logger.debug("transaction success id: +"+conversion.getId());				
            return Response.ok(responseDTO).build();
			
		} catch (Exception e) {
			logger.error("error on conversion currency", e);
            return Response.status(Status.BAD_REQUEST).entity(new JsonObject().put("sucess", false).put("info", e.getMessage())).build();	
		}


	}

    private String getKeyCurrency(OperationDTO operationDTO) throws Exception{

        try {
            return String.format("%s%s", 
            operationDTO.getCurrencySource().toUpperCase(),
            operationDTO.getCurrencyTarget().toUpperCase());   
        } catch (Exception e) {
            logger.error("error on getKeyCurrency ",e);
            throw e;
        }
    }


    public Uni<Response> doConversion(@RequestBody OperationDTO operationDTO) {
			
        logger.debug("start conversion currency from coinlayer");
        

         try{
             logger.debug(String.format("coinlayer informations: url: %s  / key : %s",this.URL_COINLAYER_API, this.KEY_COIN_LAYER_API));
             logger.debug(String.format("data sent [%s]", JsonObject.mapFrom(operationDTO)));
             //validateCurrencies(operationDTO);
             User user = findUser(operationDTO);
             
             return client               
               .getAbs(this.URL_COINLAYER_API)
               .ssl(false)
               .addQueryParam("access_key", this.KEY_COIN_LAYER_API)
               .addQueryParam("currencies", operationDTO.getCurrencyTarget())               
               .addQueryParam("source", operationDTO.getCurrencySource())
              //.addQueryParam("access_key", "63c740aa238cc259257d005a3683f61c")
               //.addQueryParam("currencies", "USD")
               //.addQueryParam("source", "BRL")
               .addQueryParam("format", "1")

               .send().onItem().transform(resp -> {

                 int statusCode = resp.statusCode();

                 logger.debug("Received response with status code: " + resp.statusCode());
             
                 if(statusCode == 200){
                     logger.debug("Inside of onItem");

                     logger.debug(resp.bodyAsString());

                     try {
                         OperationResultDTO dto = resp.bodyAsJson(OperationResultDTO.class);
                         if(dto.getSuccess()== false){
                             logger.debug("success flag: false ");	
                             return Response.status(Status.BAD_REQUEST).entity(new JsonObject()
                             .put("code", dto.getError().getCode())
                             .put("message", dto.getError().getInfo())).build();		
                         }
                         else{
                             logger.debug(String.format("api result [%s] -> [%s]", this.URL_COINLAYER_API,JsonObject.mapFrom(dto)));
                             return persistConversion(operationDTO,dto,user);
                             //return Response.ok().entity(resp.bodyAsJsonObject()).build();
                         }
                             
                             
                     } catch (Exception e) {
                        String msg = String.format("error on conversion object : %s",e.getMessage());
                        
                         logger.error("error on conversion object ",e);	
                         return msgToResponse(msg,statusCode, Status.BAD_REQUEST);
                     }
                     

                     //return Response.ok(resp.bodyAsJsonObject()).build();
                 }else{
                     String msg = String.format(" Problem accessing url [%s]  ",this.URL_COINLAYER_API);
                     logger.error(msg);
                     return  msgToResponse(msg,statusCode, Status.BAD_REQUEST);
                 }

                 
               });

               

         }catch(Exception e){
             logger.error("error ",e);	
             return exceptionToResponse(e);	
         }
         

        }

    private Response msgToResponse(String msg,int code, Status status){
        return Response.status(status).entity(new JsonObject()
        .put("sucess", false)
        .put("code", code)
        .put("info",msg)).build();
    }
        
	private Uni<Response> exceptionToResponse(Exception e){
		return Uni.createFrom().item(Response.status(Status.BAD_REQUEST)
        .entity(new JsonObject()
		.put("successs", false)
		.put("info", e.getMessage()))
        .build());
	}

    private void validateCurrencies( OperationDTO operationDTO) throws Exception{
        
        if(StringUtils.isEmpty(operationDTO.getCurrencySource()) ||
            StringUtils.isEmpty(operationDTO.getCurrencyTarget())){
                String msg = String.format("invalid or empty coins. The coins must be on the allowed list [%s]",this.LIST_CURRENCIES_ALLOWED );
            throw new Exception(msg);
        }else if(!listCurrenciessAllowed.contains(operationDTO.getCurrencySource().trim()) ||
        !listCurrenciessAllowed.contains(operationDTO.getCurrencyTarget().trim())){
            String msg = String.format("currency not found list of allowed currencies [%s]", this.LIST_CURRENCIES_ALLOWED);
            throw new Exception(msg);
        }
    }


    @GET
	@Path("/test6")
	public Uni<Response> testex6() {
			
		   logger.debug("Iniciando a conversao teste 6");
		   
		    // Send a GET request

			try{
				return client
				  .getAbs(this.URL_COINLAYER_API)
				  .ssl(false)
				  //.expect(ResponsePredicate.SC_SUCCESS)
				  //.expect(ResponsePredicate.JSON)
				  //.expect(methodsPredicate)
				  .addQueryParam("access_key", "63c740aa238cc259257d005a3683f61c")
				  .addQueryParam("currencies", "BRL")
				  .addQueryParam("source", "USD")
				  .addQueryParam("format", "1")

				  .send().onItem().transform(resp -> {

					int statusCode = resp.statusCode();
					logger.debug("Dentro do onItem");
					System.out.println("Dentro do onItem");
					if(statusCode == 200){
						return Response.ok(resp.bodyAsJsonObject()).build();
					}else if(statusCode == 400){
						logger.error("Err no testx6 : status 400 ");
						return Response.status(Status.BAD_REQUEST).build();
					}

					return Response.ok().build();
				  });

				  

			}catch(Exception e){
				logger.error("error ",e);	
			}
			return null;
        }



        private static Double calculateconversion(Double valueSource,Double valueTarget){

            return valueSource * valueTarget;
        }



        public static void main(String args[]) {
            System.out.println(calculateconversion(10D, 5.3D));
        }
    
}
