/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>Ingrediente class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */

@XmlRootElement
@Entity
public class Ingrediente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIngrediente;
    private String nomeIngrediente;
    private Boolean adicional;
    private Double valorAdicional;
    private Long empresa;

    /**
     * <p>Getter for the field <code>idIngrediente</code>.</p>
     *
     * @return a {@link java.lang.Long} object.
     */
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * <p>Setter for the field <code>idIngrediente</code>.</p>
     *
     * @param idIngrediente a {@link java.lang.Long} object.
     */
    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * <p>Getter for the field <code>nomeIngrediente</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    /**
     * <p>Setter for the field <code>nomeIngrediente</code>.</p>
     *
     * @param nomeIngrediente a {@link java.lang.String} object.
     */
    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    /**
     * <p>Getter for the field <code>adicional</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getAdicional() {
        return adicional;
    }

    /**
     * <p>Setter for the field <code>adicional</code>.</p>
     *
     * @param adicional a {@link java.lang.Boolean} object.
     */
    public void setAdicional(Boolean adicional) {
        this.adicional = adicional;
    }

    /**
     * <p>Getter for the field <code>valorAdicional</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getValorAdicional() {
        return valorAdicional;
    }

    /**
     * <p>Setter for the field <code>valorAdicional</code>.</p>
     *
     * @param valorAdicional a {@link java.lang.Double} object.
     */
    public void setValorAdicional(Double valorAdicional) {
        this.valorAdicional = valorAdicional;
    }

    /**
     * <p>Getter for the field <code>empresa</code>.</p>
     *
     * @return a {@link java.lang.Long} object.
     */
    public Long getEmpresa() {
        return empresa;
    }

    /**
     * <p>Setter for the field <code>empresa</code>.</p>
     *
     * @param empresa a {@link java.lang.Long} object.
     */
    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idIngrediente);
        hash = 59 * hash + Objects.hashCode(this.nomeIngrediente);
        hash = 59 * hash + Objects.hashCode(this.adicional);
        hash = 59 * hash + Objects.hashCode(this.valorAdicional);
        hash = 59 * hash + Objects.hashCode(this.empresa);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingrediente other = (Ingrediente) obj;
        if (!Objects.equals(this.nomeIngrediente, other.nomeIngrediente)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.idIngrediente, other.idIngrediente)) {
            return false;
        }
        if (!Objects.equals(this.adicional, other.adicional)) {
            return false;
        }
        if (!Objects.equals(this.valorAdicional, other.valorAdicional)) {
            return false;
        }
        return true;
    }

    
    
    /**
     * <p>Constructor for Ingrediente.</p>
     */
    public Ingrediente() {
    }

}
