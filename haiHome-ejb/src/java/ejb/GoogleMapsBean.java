/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Stateless
public class GoogleMapsBean implements GoogleMapsBeanLocal {
//Server key con api geolocalizzazione e google place

    private final String key = "key=" + "AIzaSyAriKS9JSx0EdrZcTq98fGldD6KzDPiPHM";
    
    @Override
    //Buisness method per la geocodifica di un indirizzo secondo le coordinate di google maps
    // return double[2] or null double[0]=latitudine double[1]=longitudine
    public double[] geocodingAddress(String address) {
        try {
            //costruzione url di richiesta
            String input = "address=" + address;
            String url = "https://maps.googleapis.com/maps/api/geocode/json?" + input + "&" + key;
            //ottenimento JSON object risultato, campi presenti status e results
            JSONObject json = readJsonFromUrl(url);
            
            String status = (String) json.get("status");

            //verifica esito richiesta status==OK
            if (status.equalsIgnoreCase("OK")) {
                //Navigazione al interno della struttura json risultato per il reperimento 
                //dei campi necessari (lat,lng)
                JSONArray res = (JSONArray) json.get("results");
                JSONObject dic = (JSONObject) res.get(0);
                JSONObject geometry = (JSONObject) dic.get("geometry");                
                JSONObject location = (JSONObject) geometry.get("location");
                /*JSONArray addresscomponent = dic.getJSONArray("address_components");
                for (int i = 0; i < addresscomponent.length(); i++) {
                    System.out.println(addresscomponent.getJSONObject(i));
                }*/
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                double[] result = new double[2];
                result[0] = lat;
                result[1] = lng;
                return result;
            }
        } catch (IOException ex) {
            Logger.getLogger(GoogleMapsBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(GoogleMapsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public ArrayList<JSONObject> getSupermarketNearBy(double lat, double lng, double rad) {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            String types = "types=grocery_or_supermarket";
            //Possibili Type se si chiedono più tipi contemporaneamente separarli con | (es. types=grocery_or_supermarket|atm)
            //accounting,airport,amusement_park,aquarium,art_gallery,!!atm!!,bakery
            //bank,bar,beauty_salon,bicycle_store,book_store,bowling_alley,bus_station
            //cafe,campground,car_dealer,car_rental,car_repair,car_wash,casino,cemetery
            //church,city_hall,clothing_store,convenience_store,courthouse,dentist,department_store
            //doctor,electrician,electronics_store,embassy,establishment,finance,fire_station,florist
            //food,funeral_home,furniture_store,gas_station,general_contractor,grocery_or_supermarket
            //gym,hair_care,hardware_store,health,hindu_temple,home_goods_store,hospital,insurance_agency
            //jewelry_store,laundry,lawyer,library,liquor_store,local_government_office,locksmith,lodging
            //meal_delivery,meal_takeaway,mosque.movie_rental,movie_theater.moving_company,museum,night_club
            //painter,park,parking,pet_store,pharmacy,physiotherapist,place_of_worship,plumber,police
            //post_office,real_estate_agency,restaurant,roofing_contractor,rv_park,school,shoe_store
            //shopping_mall,spa,stadium.storage,!!store!!,subway_station,synagogue,taxi_stand,train_station
            //travel_agency,university,veterinary_care,zoo
            String location = "location=" + lat + "," + lng;
            String radius = "radius=" + rad;
            String parameters = location + "&" + radius + "&" + types + "&" + key;
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + parameters;
            JSONObject json = readJsonFromUrl(url);
            
            String status = json.getString("status");
            if (status.equalsIgnoreCase("OK")) {
                JSONArray results = json.getJSONArray("results");
                //Elaborazione dei risultati ottenuti, problema: google restituisce 20 risultati alla volta
                do {
                    for (int i = 0; i < results.length(); i++) {
                        //generazione JSON object di output, verrà append alla lista di output
                        JSONObject res_i = results.getJSONObject(i);
                        JSONObject get = new JSONObject();
                        get.accumulate("location", res_i.getJSONObject("geometry").get("location"));
                        get.accumulate("name", res_i.getString("name"));
                        get.accumulate("vicinity", res_i.getString("vicinity"));
                        list.add(get);
                    }
                    //viene posto results a null poichè sono stati eleborati tutti 
                    results = null;
                    //Se i risultati non sono terminati l'oggetto di risposta conterrà un campo 
                    //next_page_token che serve per richedere gli altri 
                    if (json.has("next_page_token")) {
                        //google impedisce le richieste dirette di più risultati ma bisogna attendere
                        //se non si attende ritorna un json object con status="INVALID REQUEST"
                        //attesa ottenuta tramite Thred.sleep(x)
                        try {
                            Thread.sleep(2000);                 //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        //costruzione url richiesta di altri risultati
                        String pagetoken = "pagetoken=" + json.getString("next_page_token");
                        String next_page = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + pagetoken + "&" + key;
                        json = readJsonFromUrl(next_page);
                        if (json.getString("status").equalsIgnoreCase("OK")) {
                            //se lo status=OK si riempie results con i nuovi risultati
                            results = json.getJSONArray("results");
                            //altrimenti sarà settato a null dal istruzione prima del blocco di richiesta
                            //nuova pagina risultati
                        }
                    }
                } while (results != null);
                /*for (JSONObject obj : list) {
                 System.out.println(obj);
                 }*/
            }
        } catch (IOException ex) {
            Logger.getLogger(GoogleMapsBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(GoogleMapsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
