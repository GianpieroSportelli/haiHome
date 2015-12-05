/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.FiltroAppartamento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gianp_000
 */
@Local
public interface FiltroAppartamentoFacadeLocal {

    void create(FiltroAppartamento filtroAppartamento);

    void edit(FiltroAppartamento filtroAppartamento);

    void remove(FiltroAppartamento filtroAppartamento);

    FiltroAppartamento find(Object id);

    List<FiltroAppartamento> findAll();

    List<FiltroAppartamento> findRange(int[] range);

    int count();
    
}
