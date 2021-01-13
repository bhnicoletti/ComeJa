/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import dao.PessoaDAO;
import dao.VendaDAO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Pessoa;
import model.Venda;
import util.Criptografia;

/**
 * <p>
 * LoginBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String modal = "modal";
    private String email;
    private String senha;
    private boolean loggedUsuario = false;
    private boolean loggedADM = false;
    private boolean loggedEmpresa = false;
    private Pessoa pessoaAtual;
    private Date dataatual;
    private String link;
    private String titulolink;
    private List<Venda> pedidosAceitos;

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        senha = Criptografia.criptografar(senha);
        this.senha = senha;
    }

    public List<Venda> getPedidosAceitos() {
        return pedidosAceitos;
    }

    public void setPedidosAceitos(List<Venda> pedidosAceitos) {
        this.pedidosAceitos = pedidosAceitos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedUsuario() {
        return loggedUsuario;
    }

    public void setLoggedUsuario(boolean loggedUsuario) {
        this.loggedUsuario = loggedUsuario;
    }

    public boolean isLoggedADM() {
        return loggedADM;
    }

    public void setLoggedADM(boolean loggedADM) {
        this.loggedADM = loggedADM;
    }

    public boolean isLoggedEmpresa() {
        return loggedEmpresa;
    }

    public void setLoggedEmpresa(boolean loggedEmpresa) {
        this.loggedEmpresa = loggedEmpresa;
    }

    public Pessoa getPessoaAtual() {
        return pessoaAtual;
    }

    public void setPessoaAtual(Pessoa pessoaAtual) {
        this.pessoaAtual = pessoaAtual;
    }

    public Date getDataatual() {
        return dataatual;
    }

    public void setDataatual(Date dataatual) {
        this.dataatual = dataatual;
    }

    public void logar() throws IOException {
        LoginDAO dao = new LoginDAO();
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("idEmpresa", "");
        this.pessoaAtual = dao.logar(email, senha);
        if (pessoaAtual != null) {

            if (null != pessoaAtual.getStatusPessoa()) {
                switch (pessoaAtual.getTipoPessoa()) {
                    case "Administrador":
                        this.setLoggedADM(true);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.comeja.com.br/paginasADM/home.jsf");
                        break;
                    case "Empresa":
                        this.setLoggedEmpresa(true);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.comeja.com.br/paginasEmpresa/home.jsf");
                        break;
                    case "Cliente":
                        this.setLoggedUsuario(true);
                        link = "admCliente.jsf";
                        titulolink = "Meu Perfil";
                        verificarPedidos();
                        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.comeja.com.br/paginasUsuario/home.jsf");
                        modal = "modalLogin";
                        break;
                    default:

                        break;
                }
            }
        } else {
            this.email = null;
            this.senha = null;
            adicionarAvisoFlutuante("Verifique os dados digitados!");
        }
    }

    public void adicionarAvisoFlutuante(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                mensagem, "");
        context.addMessage("", msg);
    }

    public void recarregarLogin() {
        PessoaDAO pDAO = new PessoaDAO();
        pessoaAtual = pDAO.carregar(pessoaAtual.getIdPessoa());
    }

    public void deslogar() throws IOException {
        link = "cadastroCliente.jsf";
        titulolink = "CadastrarJá";
        this.setLoggedUsuario(false);
        this.setLoggedEmpresa(false);
        this.setLoggedADM(false);
        this.email = null;
        this.senha = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.comeja.com.br/paginasUsuario/home.jsf");
        modal = "modal";
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LoginBean() {
        link = "cadastroCliente.jsf";
        titulolink = "CadastrarJá";
        pedidosAceitos = new ArrayList<>();
    }

    public String getTitulolink() {
        return titulolink;
    }

    public void setTitulolink(String titulolink) {
        this.titulolink = titulolink;
    }

    public void verificarPedidos() {
        if (loggedUsuario) {
            VendaDAO vDAO = new VendaDAO();
            pedidosAceitos = vDAO.listarNotificarUsuario(pessoaAtual);
            
        }
    }

   

}
