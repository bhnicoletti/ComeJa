/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Nicoletti
 */
@ManagedBean
@ViewScoped
public class EnviaEmailBean {

    private String nome;
    private String telefone;
    private String email;
    private String mensagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EnviaEmailBean() {

    }

    public void enviaEmail() {
        HtmlEmail emailEnvio = new HtmlEmail();
        emailEnvio.setSSLOnConnect(true);
        emailEnvio.setHostName("mail.comeja.com.br");
        emailEnvio.setSslSmtpPort("587");
        emailEnvio.setAuthentication("contato@comeja.com.br", "_F823BC27B1");
        try {
            emailEnvio.setFrom("contato@comeja.com.br");
            emailEnvio.setDebug(true);
            emailEnvio.setSubject("Contato - ComeJá");

            StringBuilder builder = new StringBuilder();
            builder.append("<h1>Contato - ComeJá</h1>");
            builder.append("<br></br>Email: ").append(email);
            builder.append("<br></br>Nome: ").append(nome);
            builder.append("<br></br>Telefone: ").append(telefone);
            builder.append("<br></br>").append(mensagem);
            emailEnvio.setHtmlMsg(builder.toString());
            emailEnvio.addTo("contato@comeja.com.br");
            emailEnvio.send();
        } catch (EmailException ex) {
            ex.printStackTrace();
        }

    }

}
