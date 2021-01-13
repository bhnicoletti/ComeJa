/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PessoaDAO;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.Pessoa;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Nicoletti
 */
@RequestScoped
@ManagedBean
public class RecuperarSenhaBean {

    private Pessoa pessoa;
    private String cpf;
    private String email;
    private PessoaDAO pDAO;
    FacesContext context = FacesContext.getCurrentInstance();

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @PostConstruct
    private void init() {
        pessoa = new Pessoa();
        pDAO = new PessoaDAO();
    }

    public RecuperarSenhaBean() {
        init();
    }

    public void recuperar() {
        pessoa = pDAO.pesquisarEmail(email);
        if (pessoa != null) {
            Random random = new Random();
            int nAleatorio = random.nextInt(100000000);
            pessoa.setSenhaPessoa("" + nAleatorio);
            HtmlEmail email = new HtmlEmail();
            email.setSSLOnConnect(true);
            email.setHostName("server27.integrator.com.br");
            email.setSslSmtpPort("465");
            email.setAuthentication("contato@comeja.com.br", "_F823BC27B1");
            try {
                email.setFrom("contato@comeja.com.br", "ComeJá");
                email.setDebug(true);
                email.setSubject("Recuperação de Senha - ComeJá");

                StringBuilder builder = new StringBuilder();
                builder.append("<h1>Recuperação de senha ComeJá</h1>");
                builder.append("<p>Olá ").append(pessoa.getNomeFantasiaPessoa()).append(", você esqueceu sua senha, e solitou uma nova senha, abaixo está sua nova senha,"
                        + " por favor anote-a, e após acessar o site altera a senha para qual desejar. ");
                builder.append("<br></br>Email: ").append(pessoa.getEmailPessoa());
                builder.append("<br></br>Nova senha: ").append(nAleatorio);
                builder.append("<br></br> Att. <br></br>ComeJá</p>");
                email.setHtmlMsg(builder.toString());
                email.addTo(pessoa.getEmailPessoa());
                email.send();
                pDAO.atualizar(pessoa);
            } catch (EmailException ex) {
                ex.printStackTrace();
            }
            context.addMessage(null, new FacesMessage("Foi enviado uma nova senha para seu email, caso não encontre verifique sua caixa de spam: "));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/home.jsf");
            } catch (IOException ex) {
                Logger.getLogger(RecuperarSenhaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            context.addMessage(null, new FacesMessage("Email não encontrado, verifique se foi digitado corretamente"));
        }
    }
}
