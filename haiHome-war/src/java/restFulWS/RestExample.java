/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFulWS;

import ejb.GestoreRicercaLocal;
import ejb.GoogleMapsBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author SPORT
 */
@Path("test")
@RequestScoped
public class RestExample {
    
    @EJB
    GoogleMapsBeanLocal gmb;
    
    @EJB
    GestoreRicercaLocal gestoreRicerca;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestExample
     */
    public RestExample() {
        System.out.println("CREATE");
    }

    /**
     * Retrieves representation of an instance of restFulWS.RestExample
     * @return an instance of java.lang.String
     */
    @GET
    @Path("json/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        String json="";
        JSONObject obj=new JSONObject();
        try {
            obj.accumulate("test", "ok");
        } catch (JSONException ex) {
            Logger.getLogger(RestExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        json=obj.toString();
        return json;
    }

    /**
     * PUT method for updating or creating an instance of RestExample
     * @param content representation for the resource
     */
    @POST
    @Path("json-put/")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean putJson(String content) {
        JSONObject obj=null;
        try {
            obj=new JSONObject(content);
        } catch (JSONException ex) {
            Logger.getLogger(RestExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(obj!=null){
            System.out.println("put OK!!");
            System.out.println(obj.toString());
            return true;
        }
        return false;
    }
    
    @GET
    @Path("ping/{request}")
    public String testRest(@PathParam("request") String test){
        return "I'm in!!!: "+test;
    }
    
    @GET
    //@Path("ping/{request}")
    public String test(){
        return "I'm in!!!, WebService haiHome!! online ";
    }
    
    @GET
    @Path("geo/{request}")
    public String testGoogleMapsBean(@PathParam("request") String address){
        String first= "I'm in!!!: "+address;
        String response="";
        double[] latlng=gmb.geocodingAddress(address);
        response=first+"\n"+latlng[0]+","+latlng[1];
        return response;
    }
    
    @GET
    @Path("ricerca/setCity/{city}")
    public boolean selezionaCittà(@PathParam("city") String città) {
        return gestoreRicerca.selezionaCittà(città);
    }
    
    @GET
    @Path("ricerca/getCity/")
    public String getCittà() {
        JSONObject filtro=gestoreRicerca.attualeToJSON();
       System.out.println(filtro.toString());
        try {
            return filtro.getString("Città");
        } catch (JSONException ex) {
            Logger.getLogger(RestExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Errore";
    }
}