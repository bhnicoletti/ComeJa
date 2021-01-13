/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CidadeDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Categoria;
import model.Cidade;
import org.primefaces.context.RequestContext;

/**
 * <p>CategoriaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class CidadeBean {

    private Cidade cidade;
    private CidadeDAO cDAO;

    @PostConstruct
    private void init() {
        cidade = new Cidade();
        cDAO = new CidadeDAO();
    }

    public void salvar() {

        if ("".equals(cidade.getNome())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (cidade.getId() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(cDAO.atualizar(cidade)));
            cidade = null;
            cidade = new Cidade();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(cDAO.cadastrar(cidade)));
            cidade = null;
            cidade = new Cidade();
        }
    }

   
    public void limparCampos() {
        cidade = null;
        cidade = new Cidade();
        RequestContext.getCurrentInstance().execute("abreModal();");
    }

    
    public void carregar(Cidade c) {
        this.cidade = cDAO.carregar(c.getId());
        System.out.println(cidade.getNome());
        RequestContext.getCurrentInstance().execute("abreModal();");

    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    
    public void deletar(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(cDAO.deletar(id)));
    }

   
    public List<Cidade> listarCidade() {      
        return cDAO.listar();
    }
   
    
    public CidadeBean() {
        init();
    }

}
