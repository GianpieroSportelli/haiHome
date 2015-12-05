/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FeedBack;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface FeedBackFacadeLocal {

    void create(FeedBack feedBack);

    void edit(FeedBack feedBack);

    void remove(FeedBack feedBack);

    FeedBack find(Object id);

    List<FeedBack> findAll();

    List<FeedBack> findRange(int[] range);

    int count();
    
}
