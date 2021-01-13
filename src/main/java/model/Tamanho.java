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
import javax.persistence.Id;

/**
 *
 * @author Nicoletti
 */
@Entity
public class Tamanho implements Serializable {
    @Id
    @GeneratedValue
    private Long idTamanho;
    private String tamanho;
    private Double valorTamanho;
  

    public Long getIdTamanho() {
        return idTamanho;
    }

    public void setIdTamanho(Long idTamanho) {
        this.idTamanho = idTamanho;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Double getValorTamanho() {
        return valorTamanho;
    }

    public void setValorTamanho(Double valorTamanho) {
        this.valorTamanho = valorTamanho;
    }

    public Tamanho() {
    }

   
    
    
}
