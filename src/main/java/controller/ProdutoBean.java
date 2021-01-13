/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.Pessoa;
import model.Produto;
import model.Tamanho;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import util.UploadArquivo;

@ManagedBean
@RequestScoped
public class ProdutoBean {

    private Tamanho tamanho;
    private Produto prod;
    private ProdutoDAO prodDAO;

    @PostConstruct
    private void init() {

        tamanho = new Tamanho();
        prod = new Produto();
        prodDAO = new ProdutoDAO();
    }

    public void salvar() {

        prod.setEmpresaProduto(util.Util.retornaEmpresa());
        prod.setStatusProduto("Ativo");

        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        TamanhoBean tamanhoBean = (TamanhoBean) r.getValue(c.getELContext(), null, "tamanhoBean");

        prod.setTamanhos(tamanhoBean.getListaTamanho());
        if ("".equals(prod.getNomeProduto()) || "".equals(prod.getDescricaoProduto())
                || prod.getEmpresaProduto() == null || prod.getCategoriaProduto() == null || prod.getIngredientesProduto() == null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else if (prod.getIdProduto() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(prodDAO.atualizar(prod)));
            prod = null;
            prod = new Produto();
            RequestContext.getCurrentInstance().execute("fechaModal();");
            tamanhoBean.setListaTamanho(new ArrayList());
            FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("imagem", "");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(prodDAO.cadastrar(prod)));
            prod = null;
            prod = new Produto();
            tamanhoBean.setListaTamanho(new ArrayList());
            RequestContext.getCurrentInstance().execute("fechaModal();");
            FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("imagem", "");
        }

    }

    public void limparCampos() {
        prod = null;
        prod = new Produto();
        RequestContext.getCurrentInstance().execute("abreModal();");
    }

    public void carregar(Long id) {
        this.prod = prodDAO.carregar(id);
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        TamanhoBean tamanhoBean = (TamanhoBean) r.getValue(c.getELContext(), null, "tamanhoBean");
        tamanhoBean.setListaTamanho(prod.getTamanhos());
        RequestContext.getCurrentInstance().execute("abreModal();");

    }

    public List<Produto> listar(String s) {
        List<Produto> results = new ArrayList<>();
        new ProdutoDAO().listar(s).stream().forEach((p) -> {
            results.add(p);
        });
        return results;

    }

    public void alterarStatus(Produto p) {
        if ("Inativo".equals(p.getStatusProduto())) {
            p.setStatusProduto("Ativo");
        } else if ("Ativo".equals(p.getStatusProduto())) {
            p.setStatusProduto("Inativo");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(prodDAO.atualizar(p)));
    }

    public ProdutoBean() {
        init();
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public ProdutoDAO getProdDAO() {
        return prodDAO;
    }

    public void setProdDAO(ProdutoDAO prodDAO) {
        this.prodDAO = prodDAO;
    }

    public void upload(FileUploadEvent event) {

        if (event != null) {
            try {
                Date data = new Date();
                prod.setImagemProduto(data.getTime() + ".png");
                UploadArquivo up = new UploadArquivo();
                up.copyFile(prod.getImagemProduto(), event.getFile().getInputstream());
                event = new FileUploadEvent(null, null);
            } catch (Exception ex) {
                System.out.println("Erro ao fazer upload: " + ex.getMessage());
            }
        }
    }

    

    public void carregarProdutos(Long id) throws IOException {
       // FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("idEmpresa", p.getIdPessoa());
        FacesContext.getCurrentInstance().getExternalContext().redirect("listaProdutos.jsf?idEmpresa="+id);
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

}
