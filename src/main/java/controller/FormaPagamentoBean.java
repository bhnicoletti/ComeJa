/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FormaPagamentoDAO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.FormaPagamento;
import org.primefaces.context.RequestContext;

/**
 * <p>CategoriaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class FormaPagamentoBean {

    private FormaPagamento forma;
    private FormaPagamentoDAO fDAO;

    @PostConstruct
    private void init() {
        forma = new FormaPagamento();
        fDAO = new FormaPagamentoDAO();
    }

    public void salvar() {

        if ("".equals(forma.getFormaPagamento())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (forma.getIdFormaPagamento()!= null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(fDAO.atualizar(forma)));
            forma = null;
            forma = new FormaPagamento();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(fDAO.cadastrar(forma)));
            forma = null;
            forma = new FormaPagamento();
        }
    }

   
    public void limparCampos() {
        forma = null;
        forma = new FormaPagamento();
        RequestContext.getCurrentInstance().execute("abreModalFormaPagamento();");
    }

    
    public void carregar(FormaPagamento f) {
        this.forma = fDAO.carregar(f.getIdFormaPagamento());
        System.out.println(forma.getFormaPagamento());
        RequestContext.getCurrentInstance().execute("abreModalFormaPagamento();");

    }
     public void deletar(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(fDAO.deletar(id)));
    }

     public List<FormaPagamento> listar() {      
        return fDAO.listar();
    }
   
    public FormaPagamento getForma() {
        return forma;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }

    
    public FormaPagamentoBean() {
        init();
    }

}
