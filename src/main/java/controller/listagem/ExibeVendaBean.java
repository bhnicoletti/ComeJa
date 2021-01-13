/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.VendaDAO;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Venda;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Nicoletti
 */
@ManagedBean
@ViewScoped
public class ExibeVendaBean {

    private Venda venda;
    private String retirada;
    private VendaDAO vDAO;

    public String abrirVenda(Long v) {
        venda = venda = vDAO.carregar(v);
        if (venda.getRetirada()) {
            retirada = "Retirar no Local";
        } else {
            retirada = "Para Entrega";
        }
        return "ficha.jsf";
    }

    public void imprimirFichaVenda() {
        RequestContext.getCurrentInstance().execute("imprimirVenda();");
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ExibeVendaBean() {
        venda = new Venda();
        vDAO = new VendaDAO();
    }

    public void carregarVendaSelecionada(Venda v) {
        venda = v;
        RequestContext.getCurrentInstance().execute("abreModalItens();");
        RequestContext.getCurrentInstance().execute("estilo();");
    }

    public String getRetirada() {
        return retirada;
    }

    public void setRetirada(String retirada) {
        this.retirada = retirada;
    }

    public void aceitarPedido() {

        venda.setStatusVenda("Aceito");
        if (vDAO.atualizarMobile(venda)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Pedido Aceito"));
        }
         RequestContext.getCurrentInstance().execute("estilo();");

    }

    public void recusarPedido() {
        venda.setStatusVenda("Recusado");
        if (vDAO.atualizarMobile(venda)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Pedido Recusado"));
        }
         RequestContext.getCurrentInstance().execute("estilo();");
    }

}
