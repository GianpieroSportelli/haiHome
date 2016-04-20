/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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
        return pathLettura;
    }
}
