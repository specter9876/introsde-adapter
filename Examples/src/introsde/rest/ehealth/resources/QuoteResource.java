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
@Path("/quote")

public class QuoteResource {
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
    

    @GET //ritornare solo TEXT_XML
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML })
    public String getDailyQuote() throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
        System.out.println("Getting daily quote...");
        
        ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());
        
        int responseCode = -1;
		String resp = "";
		String accept = "";
		String contentType = "";
		String result = "";
		int count = 0;
        
        String url = ""+getBaseURI();
		URL obj = new URL(url);
		HttpURLConnection  con = (HttpURLConnection) obj.openConnection();
        
		con.setRequestMethod("GET");
		accept = "application/json";
		con.setRequestProperty("Accept", accept);
		contentType = "application/json";
		con.setRequestProperty("Content-Type", contentType);
        
		System.out.println("Request for Quote: GET " + url + " Accept: " + accept + "Content-type: " + contentType);
        
		resp = QuoteResource.getConnectionOutputJSON(con);
		responseCode = con.getResponseCode();
		System.out.println("=> HTTP Status: " + responseCode);
        
        if(responseCode==200){
		System.out.println(resp);
        
        JSONObject root = new JSONObject(resp);
        
        JSONObject json =root.getJSONObject("contents");
        
        /*
         JSONObject json = new JSONObject("{\"quotes\":[{\"date\":\"2016-03-03\",\"quote\":\"We are each gifted in a unique and important way. It is our privilege and our adventure to discover our own special light\",\"author\":\"Mary Dunbar\",\"background\":\"https://theysaidso.com/img/bgs/man_on_the_mountain.jpg\",\"length\":\"121\",\"id\":\"NMrdeKJZAouC1i06aoMm3weF\",\"category\":\"inspire\",\"title\":\"Inspiring Quote of the day\",\"tags\":[\"discover\",\"inspire\",\"self\"]}]}");
         */
        //System.out.println(json);
        
       
        JSONArray j2=json.getJSONArray("quotes");
        
        JSONObject name=j2.getJSONObject(0);
        
        
        String quote=name.getString("quote");
        return quote;
        }
        else{
            return "Reached limit of dayly quote available for free account";
        }
    }
    
   
    private static String getConnectionOutputJSON(HttpURLConnection con)
    throws IOException, TransformerException, ParserConfigurationException, SAXException {
		//PrintWriter writer = new PrintWriter("out.json", "UTF-8");
        System.out.println("FLAAAGGGGG00000");
        
        try{
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
        System.out.println("FLAAAGGGGG11111");
        
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        System.out.println("FLAAAGGGGG22222222");
		in.close();
		String retval = new GsonBuilder().setPrettyPrinting().create()
        .toJson(new JsonParser().parse(response.toString()));
        System.out.println("FLAAAGGGGG33333");
		//writer.write(retval);
		//writer.close();
		return retval;
        }
        catch(Exception ex){
            return "";
        }
	}

    
    private static URI getBaseURI() {
		return UriBuilder.fromUri("http://quotes.rest/qod.json").build();
	}
    


  
}