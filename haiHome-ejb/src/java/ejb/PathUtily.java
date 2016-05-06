/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author giacomocavallo
 */
public class PathUtily {
    /*
    Aggiungete delle vostre variabili per il vostro path e cambiate il return, in
    questo modo non andiamo a modificare il Gestore Annunci
    */
    
    private static final String GiacomoPath = "//Users//giacomocavallo//Desktop//foto//";
    private static final String GianpieroPath ="C://Users//SPORT//Pictures/haiHome!!//";
    private static final String EugenioPath = "C://Users//Eugenio Liso//Desktop//foto//";
    private static final String NicolaPath = "C://Users//nicol//Documents//imgHaiHome//"; 
    
/*            
    public static String getSavePhotoPath(){
        return NicolaPath;
    }
    */
    
    public static String getPhotoPath(){
        return NicolaPath;
    }
    
    public static boolean spostaFoto(File sorgente, File destinazione) throws FileNotFoundException, IOException{
        
        InputStream inStream = new FileInputStream(sorgente);
	FileOutputStream outStream = new FileOutputStream(destinazione);
        
         byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	  
    	    	outStream.write(buffer, 0, length);
    	 
    	    }
    	 
    	    inStream.close();
    	    outStream.close();
    	    
    	    //delete the original file
    	    sorgente.delete();
        
        
        
        
        
        return true;
    }
    
    public static boolean eliminaFoto(String path){
        
        return true;
    }


    
}
