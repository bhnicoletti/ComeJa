/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoriaDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Categoria;
import org.primefaces.context.RequestContext;

/**
 * <p>CategoriaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class CategoriaBean {

    private Categoria categoria;
    private CategoriaDAO catDAO;

    @PostConstruct
    private void init() {
        categoria = new Categoria();
        catDAO = new CategoriaDAO();
    }

    /**
     * <p>salvar.</p>
     */
    public void salvar() {

        if ("".equals(categoria.getTituloCategoria())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (categoria.getIdCategoria() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(catDAO.atualizar(categoria)));
            categoria = null;
            categoria = new Categoria();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(catDAO.cadastrar(categoria)));
            categoria = null;
            categoria = new Categoria();
        }
    }

    /**
     * <p>limparCampos.</p>
     */
    public void limparCampos() {
        categoria = null;
        categoria = new Categoria();
        RequestContext.getCurrentInstance().execute("abreModal();");
    }

    /**
     * <p>carregar.</p>
     *
     * @param c a {@link model.Categoria} object.
     */
    public void carregar(Categoria c) {
        this.categoria = catDAO.carregar(c.getIdCategoria());
        System.out.println(categoria.getTituloCategoria());
        RequestContext.getCurrentInstance().execute("abreModal();");

    }

    /**
     * <p>deletar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void deletar(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(catDAO.deletar(id)));
    }

    /**
     * <p>Getter for the field <code>categoria</code>.</p>
     *
     * @return a {@link model.Categoria} object.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * <p>Setter for the field <code>categoria</code>.</p>
     *
     * @param categoria a {@link model.Categoria} object.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * <p>listarCategoria.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Categoria> listarCategoria() {      
        return catDAO.listar();
    }
    /**
     * <p>Constructor for CategoriaBean.</p>
     */
    public CategoriaBean() {
        init();
    }

}
