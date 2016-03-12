package introsde.rest.ehealth;

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


public class TestClientQuote {
    public static int firstpersonid;
    public static int lastpersonid;
    public static int POSTpersonid;
    public static List<String> array=new ArrayList<>();
    public static int measureTypeid;
    public static String measureType;
    //public static HelperClient ch =new HelperClient();
    
	public static void main(String[] args)throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
		
        
        ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());
		
        System.out.println("STEP 1: URL of restfull web service: "+getBaseURI());
        
        System.out.println("=====================================================");

        getRequest1();
        
		
	}
    
    
    
	private static void getRequest1() throws IOException, JAXBException, SAXException, TransformerException, ParserConfigurationException {
		
       
		int responseCode = -1;
		String resp = "";
		String accept = "";
		String contentType = "";
		String result = "";
		int count = 0;
		
        System.out.println("===============================================================================================");
		//For JSON
    
        String url = ""+getBaseURI();
		URL obj = new URL(url);
		HttpURLConnection  con = (HttpURLConnection) obj.openConnection();
        
		con.setRequestMethod("GET");
		accept = "application/json";
		con.setRequestProperty("Accept", accept);
		contentType = "application/json";
		con.setRequestProperty("Content-Type", contentType);
        
		System.out.println("Request #3.1: GET " + url + " Accept: " + accept + "Content-type: " + contentType);
		      
		resp = TestClientQuote.getConnectionOutputJSON(con);
		responseCode = con.getResponseCode();
		System.out.println("=> HTTP Status: " + responseCode);
		//System.out.println(resp);
        
        //System.out.println("ESPERIMENTIIIII");
        
        JSONObject root = new JSONObject(resp);
        
        JSONObject json =root.getJSONObject("contents");
        
        /*
        JSONObject json = new JSONObject("{\"quotes\":[{\"date\":\"2016-03-03\",\"quote\":\"We are each gifted in a unique and important way. It is our privilege and our adventure to discover our own special light\",\"author\":\"Mary Dunbar\",\"background\":\"https://theysaidso.com/img/bgs/man_on_the_mountain.jpg\",\"length\":\"121\",\"id\":\"NMrdeKJZAouC1i06aoMm3weF\",\"category\":\"inspire\",\"title\":\"Inspiring Quote of the day\",\"tags\":[\"discover\",\"inspire\",\"self\"]}]}");
        */
        //System.out.println(json);
        
   
         //JSONObject j1=json.getJSONObject("quotes");
        JSONArray j2=json.getJSONArray("quotes");
      
        JSONObject name=j2.getJSONObject(0);
        
        System.out.println(name.getString("quote"));
            
        

       //rimettere perche ho superato il limite di richieste JSONObject hits = json.getJSONObject("contents");
        
        
        
        
        
	}
    
    
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://quotes.rest/qod.json").build();
	}
    
    
    
	private static String getConnectionOutputXML(HttpURLConnection con)
    throws IOException, TransformerException, ParserConfigurationException, SAXException {
		PrintWriter writer = new PrintWriter("out.xml", "UTF-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
        
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			writer.println(inputLine);
		}
		in.close();
		writer.close();
        
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse("out.xml");
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(outputStream, "UTF-8")));
        
		return outputStream.toString();
	}
    
    
    
	private static String getConnectionOutputJSON(HttpURLConnection con)
    throws IOException, TransformerException, ParserConfigurationException, SAXException {
		PrintWriter writer = new PrintWriter("out.json", "UTF-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
        
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String retval = new GsonBuilder().setPrettyPrinting().create()
        .toJson(new JsonParser().parse(response.toString()));
		writer.write(retval);
		writer.close();
		return retval;
	}
    
   // private static String getFromJSON(String resp){
        
        
   // }
    
    
	public static int countStringOccurence(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;
		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}
		return count;
	}
}


