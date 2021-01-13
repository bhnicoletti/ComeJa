/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * <p>RelatorioVendaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class RelatorioVendaBean {

    private String datapesquisainicial;
    private String datapesquisafinal;

    public String getDatapesquisainicial() {
        return datapesquisainicial;
    }

    public void setDatapesquisainicial(String datapesquisainicial) {
        this.datapesquisainicial = datapesquisainicial;
    }

    public String getDatapesquisafinal() {
        return datapesquisafinal;
    }

    public void setDatapesquisafinal(String datapesquisafinal) {
        this.datapesquisafinal = datapesquisafinal;
    }

  
    public void gerarRelatorio() throws ParseException {     
       
        try {
            
            
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
            session.setAttribute("caminho", "relatorioVendasComeJa.jasper");
            session.setAttribute("empresa", Long.parseLong(util.Util.retornaEmpresaFixa().getIdPessoa().toString()));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsedInicial = format.parse(datapesquisainicial);
            Date parsedFinal = format.parse(datapesquisafinal);
            session.setAttribute("dataInicial", parsedInicial);
            session.setAttribute("dataFinal", parsedFinal);
            session.setAttribute("tipo", "venda");
            util.Util.redirect("/ExemploRelatorioServlet");

        } catch (ExceptionInInitializerError ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

    

    
    /**
     * <p>Constructor for RelatorioVendaBean.</p>
     */
    public RelatorioVendaBean() {
    }

}
