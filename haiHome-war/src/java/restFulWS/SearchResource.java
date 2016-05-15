/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFulWS;

import ejb.GestoreImmaginiLocal;
import ejb.GestoreRicercaLocal;
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
@Path("search")
@RequestScoped
public class SearchResource {

    @EJB
    GestoreRicercaLocal gestoreRicerca;

    @EJB
    GestoreImmaginiLocal gestoreImmagini;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SearchResource
     */
    public SearchResource() {
        System.out.println("Create Resource Search");
    }

    @GET
    //@Path("ping/{request}")
    public String start() {
        return "I'm in!!!, WebService haiHome!!-Search online ";
    }

    /**
     * Retrieves representation of an instance of restFulWS.SearchResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("json/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        String json = "";
        JSONObject obj = new JSONObject();
        try {
            obj.accumulate("test", "ok");
        } catch (JSONException ex) {
            Logger.getLogger(RestExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        json = obj.toString();
        return json;
    }

    /**
     * PUT method for updating or creating an instance of SearchResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Path("ping/{request}")
    public String testRest(@PathParam("request") String test) {
        return "I'm in!!!: " + test;
    }
    

    @POST
    @Path("getFilter/")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String search(String content) throws JSONException {
        System.out.println("SEARCH");
        JSONObject obj = null;
        obj = new JSONObject(content);
        JSONObject result=gestoreRicerca.create_useFilter(obj);
        System.out.println("on resource: "+result.toString());
        return result.toString();
    }
    
    @GET
    @Path("getQuartieri/{city}")
    public String getQuartieri(@PathParam("city") String city){
        System.out.println("GET QUARTIERI");
        JSONObject quartieri=gestoreRicerca.getQuartieri(city);
        System.out.println(quartieri.toString());
        return quartieri.toString();
    }
    
    @GET
    @Path("getTipoStanza/")
    public String getTipoStanza(){
        System.out.println("GET TipoStanza");
        JSONObject tipoStanza=gestoreRicerca.getTipoStanzaJSON();
        System.out.println(tipoStanza.toString());
        return tipoStanza.toString();
    }
    
    @POST
    @Path("getImage/")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String getImage(String content) throws JSONException {
        System.out.println("GET IMAGE");
        JSONObject obj = null;
        obj = new JSONObject(content);
        System.out.println(obj.toString());
        if(obj.has("url")){
            String res=gestoreImmagini.getImage(obj.getString("url"), obj.getString("ext"));
            if(res!=null){
                System.out.println("Image "+obj.getString("url")+" loaded");
                return res;
            }else{
              return "";  
            }   
        }else{
            return null;
        }
    }
}
