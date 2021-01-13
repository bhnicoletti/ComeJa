/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConfiguracaoDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Configuracao;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Nicoletti
 */
@ManagedBean
@ViewScoped
public class ConfiguracaoBean {

    private Configuracao config;
    ConfiguracaoDAO dao;
    private List<Configuracao> lista;

    public void salvar() {
        dao.salvar(config);
        RequestContext.getCurrentInstance().execute("fechaModalConfiguracao();");
    }

    public void carregar(Long id) {
        config = dao.carregar(id);
        RequestContext.getCurrentInstance().execute("abreModalConfiguracao();");
    }

    public Configuracao getConfig() {
        return config;
    }

    public void setConfig(Configuracao config) {
        this.config = config;
    }

    public void limparCampos() {
        config = new Configuracao();
        RequestContext.getCurrentInstance().execute("abreModalConfiguracao();");
    }

    

    

    public List<Configuracao> getLista() {
        
        lista = dao.listarPaginaInicial();
        return lista;
    }

    public void setLista(List<Configuracao> lista) {
        this.lista = lista;
    }

    
    @PostConstruct
    private void init() {
        config = new Configuracao();
        dao = new ConfiguracaoDAO();
    }
    public ConfiguracaoBean() {
       init();
    }
    
    

}
