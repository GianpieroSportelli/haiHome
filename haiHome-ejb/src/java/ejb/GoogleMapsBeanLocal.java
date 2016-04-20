/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import javax.ejb.Local;
import org.json.JSONObject;

/**
 *
 * @author gianp_000
 */
@Local
public interface GoogleMapsBeanLocal {

    double[] geocodingAddress(String address);

    ArrayList<JSONObject> getSupermarketNearBy(double lat, double lng, double rad);

    String getQuartiereByAddress(String address);

    public ArrayList<JSONObject> getBankNearBy(double lat, double lng, double rad);

    public ArrayList<JSONObject> getBusNearBy(double lat, double lng, double rad);
    
}
