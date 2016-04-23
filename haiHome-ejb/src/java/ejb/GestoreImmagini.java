/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 *
 * @author SPORT
 */
@Stateless
public class GestoreImmagini implements GestoreImmaginiLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String getImage(String path, String ext) {
        String imageString = "";
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, ext, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            System.out.println("Image "+path+" loaded");
            bos.close();
            return imageString;
        } catch (IOException ex) {
            Logger.getLogger(GestoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageString;
    }

}
