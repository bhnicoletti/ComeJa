/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Nicoletti
 */
@Entity
public class Configuracao implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private Integer versaoAppAndroid;
    private Integer versaoAppEmpresa;
    private Integer versaoAppIphone;
    private String tituloMensagem;
    private String mensagemMural;    
    private boolean status;
    private boolean paginainicial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersaoAppAndroid() {
        return versaoAppAndroid;
    }

    public void setVersaoAppAndroid(Integer versaoAppAndroid) {
        this.versaoAppAndroid = versaoAppAndroid;
    }

    public Integer getVersaoAppIphone() {
        return versaoAppIphone;
    }

    public void setVersaoAppIphone(Integer versaoAppIphone) {
        this.versaoAppIphone = versaoAppIphone;
    }

    public String getTituloMensagem() {
        return tituloMensagem;
    }

    public void setTituloMensagem(String tituloMensagem) {
        this.tituloMensagem = tituloMensagem;
    }

    public String getMensagemMural() {
        return mensagemMural;
    }

    public void setMensagemMural(String mensagemMural) {
        this.mensagemMural = mensagemMural;
    }  

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPaginainicial() {
        return paginainicial;
    }

    public void setPaginainicial(boolean paginainicial) {
        this.paginainicial = paginainicial;
    }

    public Integer getVersaoAppEmpresa() {
        return versaoAppEmpresa;
    }

    public void setVersaoAppEmpresa(Integer versaoAppEmpresa) {
        this.versaoAppEmpresa = versaoAppEmpresa;
    }

  
    

    
}
