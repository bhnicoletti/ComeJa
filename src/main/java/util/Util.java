/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.LoginBean;
import dao.PessoaDAO;
import java.io.IOException;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Pessoa;

/**
 * <p>Util interface.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public interface Util {

    /**
     * <p>retornaEmpresa.</p>
     *
     * @return a {@link model.Pessoa} object.
     */
    public static Pessoa retornaEmpresa() {
        if (!"".equals(FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("idEmpresa").toString())) {
            PessoaDAO pDAO = new PessoaDAO();
            Pessoa p = pDAO.carregar(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("idEmpresa").toString()));
            return p;
        } else {
            FacesContext c = FacesContext.getCurrentInstance();
            ELResolver r = c.getApplication().getELResolver();
            LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
            return loginBean.getPessoaAtual();
        }
    }

    /**
     * <p>retornaEmpresaFixa.</p>
     *
     * @return a {@link model.Pessoa} object.
     */
    public static Pessoa retornaEmpresaFixa() {
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        return loginBean.getPessoaAtual();
    }

    /**
     * <p>redirect.</p>
     *
     * @param page a {@link java.lang.String} object.
     */
    public static void redirect(String page) {

        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ec = ctx.getExternalContext();

        try {
            ec.redirect(ec.getRequestContextPath() + page);
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
        }

    }

}
