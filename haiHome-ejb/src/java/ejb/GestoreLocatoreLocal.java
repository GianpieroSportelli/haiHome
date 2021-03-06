package ejb;

import entity.Annuncio;
import entity.Locatore;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import org.json.JSONObject;

/**
 *
 * @author Nicola Licheri
 */
@Local
public interface GestoreLocatoreLocal {
    
    public boolean aggiungiLocatore(String email, String password, String nome,
            String cognome, String telefono, String img_profilo);

    public List<String> getLocatori();

    public boolean checkLocatore(String email);

    public boolean checkLocatore(String email, String password);

    public boolean removeLocatore();

    public Collection<Annuncio> getAnnunci();

    public List<Annuncio> getAnnunciVisibili();

    public List<Annuncio> getAnnunciArchiviati();

    public List<Annuncio> getAnnunciOscurati();

    /* Gestione profilo del locatore*/
    public void modificaTelefono(String telefono);

    public void modificaDescrizione(String descrizione);

    public void modificaAvatarByURL(String img);

    public boolean modificaPassword(String oldpassword, String newpassword);

    public void modificaPassword(String newpassword);

    public boolean checkAnnuncio(Annuncio a);

    public boolean removeAnnuncio(Annuncio a);

    public boolean addAnnuncio(Annuncio a);

    public boolean updateAnnuncio(long oid, Annuncio a);

    public JSONObject toJSON();

    public Locatore getLocatore();

    public void reloadLocatore();
    
    public boolean bloccaLocatore(long oid, boolean bloccato);
}
