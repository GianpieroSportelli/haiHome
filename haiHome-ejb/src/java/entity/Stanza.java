/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author gianp_000
 */
@Entity
public abstract class Stanza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected Collection<String> foto;
        
    protected double metratura;
    
    

    /**
     * Get the value of metratura
     *
     * @return the value of metratura
     */
    public double getMetratura() {
        return metratura;
    }

    /**
     * Set the value of metratura
     *
     * @param metratura new value of metratura
     */
    public void setMetratura(double metratura) {
        this.metratura = metratura;
    }


    /**
     * Get the value of foto
     *
     * @return the value of foto
     */
    public Collection<String> getFoto() {
        return foto;
    }

    /**
     * Set the value of foto
     *
     * @param foto new value of foto
     */
    public void setFoto(Collection<String> foto) {
        this.foto = foto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stanza)) {
            return false;
        }
        Stanza other = (Stanza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Stanza[ id=" + id + " ]";
    }
    
}
