/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author SPORT
 */
@Local
public interface GestoreImmaginiLocal {

    /**
     * Restituisci Stringa corrispondente ad un imagi codificata in base64
     * @param path percorso assoluto dell'immagine
     * @param ext tipo di immagine
     * @return Stringa codificante immagine in base64
     */
    String getImage(String path, String ext);
    
}
