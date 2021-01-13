/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EnderecoDAO;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Endereco;
import org.primefaces.context.RequestContext;

/**
 * <p>EnderecoBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class EnderecoBean {

    private Endereco endereco;
    private EnderecoDAO eDAO;

    @PostConstruct
    private void init() {
        endereco = new Endereco();
        eDAO = new EnderecoDAO();
    }

    /**
     * <p>salvar.</p>
     */
    public void salvar() {

        if ("".equals(endereco.getRuaEndereco())
                || "".equals(endereco.getNumeroEndereco())
                || "".equals(endereco.getEstadoEndereco())
                || "".equals(endereco.getBairroEndereco())
                || "".equals(endereco.getCidadeEndereco())
                || "".equals(endereco.getCepEndereco())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (endereco.getIdEndereco() != null) {

            endereco.setPessoa(util.Util.retornaEmpresaFixa().getIdPessoa());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(eDAO.atualizar(endereco)));
            endereco = null;
            endereco = new Endereco();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(eDAO.cadastrar(endereco)));
            endereco = null;
            endereco = new Endereco();
        }
    }

    /**
     * <p>limparCampos.</p>
     */
    public void limparCampos() {
        endereco = null;
        endereco = new Endereco();
        RequestContext.getCurrentInstance().execute("abreModalEndereco();");
    }

    /**
     * <p>carregarEndereco.</p>
     *
     * @param c a {@link model.Endereco} object.
     */
    public void carregarEndereco(Endereco c) {
        this.endereco = eDAO.carregar(c.getIdEndereco());
        RequestContext.getCurrentInstance().execute("abreModalEndereco();");

    }

    
    public void deletar(Endereco e) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(eDAO.deletar(e)));
    }

    /**
     * <p>Getter for the field <code>endereco</code>.</p>
     *
     * @return a {@link model.Endereco} object.
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * <p>Setter for the field <code>endereco</code>.</p>
     *
     * @param endereco a {@link model.Endereco} object.
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    
    /**
     * <p>Constructor for EnderecoBean.</p>
     */
    public EnderecoBean() {
        init();
    }

}
