/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Tamanho;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Nicoletti
 */
@ManagedBean
@ViewScoped
public class TamanhoBean {

    private List<Tamanho> listaTamanho;
    private Tamanho tamanho;

    @PostConstruct
    private void init() {
        listaTamanho = new ArrayList();
        tamanho = new Tamanho();
    }

    public void adicionarTamanho() {
        listaTamanho.add(tamanho);
        tamanho = new Tamanho();
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public void removerTamanho(Tamanho tam) {
        listaTamanho.remove(tam);
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public List<Tamanho> getListaTamanho() {
        return listaTamanho;
    }

    public void setListaTamanho(List<Tamanho> listaTamanho) {
        this.listaTamanho = listaTamanho;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }
    
    

}
