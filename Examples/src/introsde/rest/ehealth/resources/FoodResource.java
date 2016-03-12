package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Food;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.json.*;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.persistence.PersistenceUnit;


@Stateless
// will work only inside a Java EE application
@LocalBean
// will work only inside a Java EE application
@Path("/food")

public class FoodResource {
    // Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
    
	// will work only inside a Java EE application
	@PersistenceUnit(unitName = "introsde-jpa")
	EntityManager entityManager;
    // Application integration
    

    ///MAGARI AGGIUNGERE UN METODO GETFOOD SENZA TIPE??
    @GET
    @Path("{foodType}")
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Food> getFoodByType(@PathParam("foodType") String foodType) throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
        System.out.println("Getting list of food...");
        
        ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());
        
        int responseCode = -1;
		String resp = "";
		String accept = "";
		String contentType = "";
		String result = "";
		int count = 0;
        String foodName="";
        String description="";
        double calories=0;
        List <Food> foodList=new ArrayList<Food>();
        
        
        String url = getBaseURI() +"?q="+foodType+"&app_id=7b82cd89&app_key=3404316bf48af888482a712b00ddc7fe";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("GET");
        accept = "application/json";
        con.setRequestProperty("Accept", accept);
        contentType = "application/json";
        con.setRequestProperty("Content-Type", contentType);
        
        System.out.println("Request FoodType: GET " + url + " Accept: " + accept
                           + "Content-type: " + contentType);
        
        resp = FoodResource.getConnectionOutputJSON(con);
        responseCode = con.getResponseCode();
        System.out.println("=> HTTP Status: " + responseCode);
        
        
        if(responseCode == 200){
            
            System.out.println(resp);
            
            JSONObject root = new JSONObject(resp);
            JSONArray hits = root.getJSONArray("hits");
            
            System.out.println("length hits: "+hits.length());
            
            for (int i = 0; i < hits.length(); i++) {
                
                Food food=new Food();
                
                
                JSONObject recipe = ((JSONObject) hits.get(i)).getJSONObject("recipe");
                System.out.println("===============================================");
                
                food.setName(recipe.getString("label"));
                food.setType(foodType);
                food.setCalories(recipe.getDouble("calories"));
                System.out.println("name: "+recipe.getString("label"));
                System.out.println("calories: "+recipe.getDouble("calories"));
                String stringa="";
                JSONArray ingredients = recipe.getJSONArray("ingredientLines");
                System.out.println("size: "+ingredients.length());
                for (int k = 0; k < ingredients.length(); k++) {
                    //food.setDescription(""+food.getDescription()+""+ingredients.get(k)+", ");
                    stringa=""+stringa+""+ingredients.get(k);
                    System.out.println(";"+ingredients.get(k));
                }
                
                System.out.println("==============END LOOP================================");
                

                System.out.println(" "+stringa);
                food.setDescription(stringa);
                System.out.println("==============FOOD ITEM================================");
                System.out.println(food);
                System.out.println("======================================================");
                foodList.add(food);
                System.out.println("==============LIST SIZE================================");
                System.out.println("size: "+foodList.size());
                System.out.println("======================================================");
            }
       
            
           
        
            return foodList;
        }
        else{
            return null;
        }
    }
   
    private static String getConnectionOutputJSON(HttpURLConnection con) throws IOException, TransformerException,
    ParserConfigurationException, SAXException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
                                                                     con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
        
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String retval = new GsonBuilder().setPrettyPrinting().create()
        .toJson(new JsonParser().parse(response.toString()));
		
		return retval;
	}
    
    private static URI getBaseURI() {
		return UriBuilder.fromUri("http://api.edamam.com/search").build();
	}

   


  
}