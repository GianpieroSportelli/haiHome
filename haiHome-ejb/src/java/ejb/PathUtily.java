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
    private static final String GianpieroPath ="C:\\Users\\SPORT\\Pictures\\haiHome!!\\";
    private static final String EugenioPath = "C://Users//Eugenio Liso//Desktop//foto//";
    private static final String GiacomoPath2 = "//Users//giacomocavallo//NetBeansProjects//ProgettoSSCSWeb//haiHome//haiHome-war//ImmaginiApp//";
    
    private static final String pathInternoS = "//Users//giacomocavallo//NetBeansProjects//ProgettoSSCSWeb//haiHome//haiHome-war//web//Immagini//AppPhoto//";

     
    private static final String pathLettura = "./ImmaginiApp//";
    private static final String pathInternoL = ".//Immagini//AppPhoto//";
            
            
    public static String getSavePhotoPath(){
        return GiacomoPath2;
    }
    
    public static String getPhotoPath(){
        return GiacomoPath;
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


    
}
