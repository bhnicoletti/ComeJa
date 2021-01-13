/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.IngredienteDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Ingrediente;
import org.primefaces.context.RequestContext;

/**
 * <p>IngredienteBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class IngredienteBean {

    private Ingrediente ingrediente;
    private IngredienteDAO ingDAO;

    @PostConstruct
    private void init() {
        ingrediente = new Ingrediente();
        ingDAO = new IngredienteDAO();
    }

    /**
     * <p>salvar.</p>
     */
    public void salvar() {
        ingrediente.setEmpresa(util.Util.retornaEmpresa().getIdPessoa());
        if ("".equals(ingrediente.getNomeIngrediente())|| "".equals(ingrediente.getAdicional()|| "".equals(ingrediente.getValorAdicional()))) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (ingrediente.getIdIngrediente()!= null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(ingDAO.atualizar(ingrediente)));
            ingrediente = null;
            ingrediente = new Ingrediente();
            RequestContext.getCurrentInstance().execute("fechaModal();");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(ingDAO.cadastrar(ingrediente)));
            ingrediente = null;
            ingrediente = new Ingrediente();
            RequestContext.getCurrentInstance().execute("fechaModal();");
        }
    }

    /**
     * <p>limparCampos.</p>
     */
    public void limparCampos() {
        ingrediente = null;
        ingrediente = new Ingrediente();
        RequestContext.getCurrentInstance().execute("abreModal();");
    }

    /**
     * <p>carregar.</p>
     *
     * @param i a {@link model.Ingrediente} object.
     */
    public void carregar(Ingrediente i) {
        this.ingrediente = ingDAO.carregar(i.getIdIngrediente());
        RequestContext.getCurrentInstance().execute("abreModal();");

    }

    /**
     * <p>deletar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void deletar(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(ingDAO.deletar(id)));
    }

    /**
     * <p>Getter for the field <code>ingrediente</code>.</p>
     *
     * @return a {@link model.Ingrediente} object.
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * <p>Setter for the field <code>ingrediente</code>.</p>
     *
     * @param ingrediente a {@link model.Ingrediente} object.
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

   /**
    * <p>listarIngrediente.</p>
    *
    * @return a {@link java.util.List} object.
    */
   public List<Ingrediente> listarIngrediente() {        
        return ingDAO.listar();
    }
   
   /**
    * <p>listarIngredienteAdicional.</p>
    *
    * @return a {@link java.util.List} object.
    */
   public List<Ingrediente> listarIngredienteAdicional() {        
        return ingDAO.listarAdicional();
    }

    /**
     * <p>Constructor for IngredienteBean.</p>
     */
    public IngredienteBean() {
        init();
    }

}
