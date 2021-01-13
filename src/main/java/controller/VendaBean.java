/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import dao.VendaDAO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Endereco;
import model.ItemVenda;
import model.Produto;
import model.Tamanho;
import model.Venda;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class VendaBean implements Serializable {

    private ItemVenda itemVenda;
    private Venda vend;
    private ArrayList<ItemVenda> carrinhoCompras;
    private Double valorTotal = 0.00;
    private Endereco endereco;
    private String novasVendas;
    private Produto prod;
    private ProdutoDAO pDAO;
    private String updateForm;
    private Double valor;
    private Boolean alcoolico;
    private Integer quantMetadeGrande;
    private Integer quantMetadeMedia;
    private Integer quantMetadePequena;
    private Integer quantMetadeMini;
    private String tamanho;
    private Tamanho tamanhoProduto;
    private Long idPreLoad;

    @PostConstruct
    private void init() {
        prod = new Produto();
        pDAO = new ProdutoDAO();
        vend = new Venda();
        carrinhoCompras = new ArrayList();
        itemVenda = new ItemVenda();
        itemVenda.setMetade(false);
        updateForm = "";
        alcoolico = false;
        quantMetadeGrande = 0;
        quantMetadeMedia = 0;
        quantMetadePequena = 0;
        quantMetadeMini = 0;
        tamanho = "";
        tamanhoProduto = new Tamanho();

    }

    public void adicionarAoCarrinhoModoUsuario() {

        if (!this.carrinhoCompras.isEmpty()) {
            if (!Objects.equals(prod.getEmpresaProduto().getIdPessoa(), carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto().getIdPessoa())) {
                adicionarAvisoFlutuante("Escolha por favor, apenas produtos de uma única empresa por pedido");
            } else {

                itemVenda.setProdutoItemVenda(prod);
                valor = tamanhoProduto.getValorTamanho();
                itemVenda.setTamanho(tamanhoProduto.getTamanho());
                if (itemVenda.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Pizza")) {
                    if (itemVenda.getMetade()) {
                        itemVenda.setQuantItemVenda(0.5);
                        String aux = "";
                        for (int i = 0; i < itemVenda.getProdutoItemVenda().getTamanhos().size(); i++) {
                            if (itemVenda.getProdutoItemVenda().getTamanhos().get(i).getValorTamanho().equals(valor)) {
                                aux = itemVenda.getProdutoItemVenda().getTamanhos().get(i).getTamanho();
                            }
                        }
                        itemVenda.setTamanho(aux);
                    }
                } else {
                    itemVenda.setMetade(false);
                }
                this.itemVenda.setVendaItemVenda(vend.getIdVenda());
                this.itemVenda.setVlrItemVenda(itemVenda.getQuantItemVenda() * valor);
                if (itemVenda.getIngredientesAdicionais() != null) {
                    itemVenda.getIngredientesAdicionais().stream().forEach((i) -> {
                        this.itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + (i.getValorAdicional() * itemVenda.getQuantItemVenda()));
                    });
                }
                ItemVenda itemremover = null;
                for (ItemVenda v : carrinhoCompras) {

                    if (v.getProdutoItemVenda().getIdProduto().equals(itemVenda.getProdutoItemVenda().getIdProduto())) {
                        if (v.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida")) {
                            Double valor = v.getQuantItemVenda();
                            Double valornovo = itemVenda.getQuantItemVenda();
                            Double valorItem = v.getVlrItemVenda();
                            itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                            itemVenda.setQuantItemVenda(valornovo + valor);
                            itemremover = v;
                        } else if (v.getIngredientesAdicionais().equals(itemVenda.getIngredientesAdicionais()) && v.getIngredientesProduto().equals(itemVenda.getIngredientesProduto())) {
                            if (v.getVlrItemVenda().equals(valor)) {
                                Double valori = v.getQuantItemVenda();
                                Double valornovo = itemVenda.getQuantItemVenda();
                                Double valorItem = v.getVlrItemVenda();
                                itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                                itemVenda.setQuantItemVenda(valornovo + valori);
                                itemremover = v;
                            }
                        }
                    }
                }

                carrinhoCompras.remove(itemremover);
                itemVenda.setVlrUnitarioProduto(valor);
                itemVenda.setVendaItemVenda(vend.getIdVenda());
                this.carrinhoCompras.add(itemVenda);
                valor = null;
                calcularCarrinho();

                RequestContext.getCurrentInstance().execute("fechaModal();");
                RequestContext.getCurrentInstance().execute("fechaModalBebida();");
                RequestContext.getCurrentInstance().execute("fechaModalPizza();");
                RequestContext.getCurrentInstance().execute("estilo();");
                this.itemVenda = new ItemVenda();
                itemVenda.setMetade(false);
            }
        } else {

            itemVenda.setProdutoItemVenda(prod);
            valor = tamanhoProduto.getValorTamanho();
            itemVenda.setTamanho(tamanhoProduto.getTamanho());
            if (itemVenda.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Pizza")) {
                if (itemVenda.getMetade()) {
                    itemVenda.setQuantItemVenda(0.5);
                    String aux = "";
                    for (int i = 0; i < itemVenda.getProdutoItemVenda().getTamanhos().size(); i++) {
                        if (itemVenda.getProdutoItemVenda().getTamanhos().get(i).getValorTamanho().equals(valor)) {
                            aux = itemVenda.getProdutoItemVenda().getTamanhos().get(i).getTamanho();
                        }
                    }
                    itemVenda.setTamanho(aux);
                }
            } else {
                itemVenda.setMetade(false);
            }
            this.itemVenda.setVendaItemVenda(vend.getIdVenda());

            this.itemVenda.setVlrItemVenda(itemVenda.getQuantItemVenda() * valor);
            if (itemVenda.getIngredientesAdicionais() != null) {
                itemVenda.getIngredientesAdicionais().stream().forEach((i) -> {
                    this.itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + (i.getValorAdicional() * itemVenda.getQuantItemVenda()));
                });
            }

            ItemVenda itemremover = null;
            for (ItemVenda v : carrinhoCompras) {

                if (v.getProdutoItemVenda().getIdProduto().equals(itemVenda.getProdutoItemVenda().getIdProduto())) {
                    if (v.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida")) {
                        Double valor = v.getQuantItemVenda();
                        Double valornovo = itemVenda.getQuantItemVenda();
                        Double valorItem = v.getVlrItemVenda();
                        itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                        itemVenda.setQuantItemVenda(valornovo + valor);
                        itemremover = v;
                    } else if (v.getIngredientesAdicionais().equals(itemVenda.getIngredientesAdicionais()) && v.getIngredientesProduto().equals(itemVenda.getIngredientesProduto())) {
                        if (v.getVlrItemVenda().equals(valor)) {
                            Double valor = v.getQuantItemVenda();
                            Double valornovo = itemVenda.getQuantItemVenda();
                            Double valorItem = v.getVlrItemVenda();
                            itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                            itemVenda.setQuantItemVenda(valornovo + valor);
                            itemremover = v;
                        }
                    }
                }
            }

            carrinhoCompras.remove(itemremover);
            itemVenda.setVlrUnitarioProduto(valor);
            itemVenda.setVendaItemVenda(vend.getIdVenda());
            this.carrinhoCompras.add(itemVenda);
            valor = null;
            calcularCarrinho();

            RequestContext.getCurrentInstance().execute("fechaModal();");
            RequestContext.getCurrentInstance().execute("fechaModalBebida();");
            RequestContext.getCurrentInstance().execute("fechaModalPizza();");
            RequestContext.getCurrentInstance().execute("estilo();");
            this.itemVenda = new ItemVenda();
            itemVenda.setMetade(false);
        }
    }

    public void adicionarAoCarrinhoModoUsuarioMobile() throws IOException {

        if (!this.carrinhoCompras.isEmpty()) {
            if (!Objects.equals(prod.getEmpresaProduto().getIdPessoa(), carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto().getIdPessoa())) {
                adicionarAvisoFlutuante("Escolha por favor, apenas produtos de uma única empresa por pedido");
            } else {

                itemVenda.setProdutoItemVenda(prod);
                valor = tamanhoProduto.getValorTamanho();
                itemVenda.setTamanho(tamanhoProduto.getTamanho());
                if (itemVenda.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Pizza")) {
                    if (itemVenda.getMetade()) {
                        itemVenda.setQuantItemVenda(0.5);
                        String aux = "";
                        for (int i = 0; i < itemVenda.getProdutoItemVenda().getTamanhos().size(); i++) {
                            if (itemVenda.getProdutoItemVenda().getTamanhos().get(i).getValorTamanho().equals(valor)) {
                                aux = itemVenda.getProdutoItemVenda().getTamanhos().get(i).getTamanho();
                            }
                        }
                        itemVenda.setTamanho(aux);
                    }
                } else {
                    itemVenda.setMetade(false);
                }
                this.itemVenda.setVendaItemVenda(vend.getIdVenda());
                this.itemVenda.setVlrItemVenda(itemVenda.getQuantItemVenda() * valor);
                if (itemVenda.getIngredientesAdicionais() != null) {
                    itemVenda.getIngredientesAdicionais().stream().forEach((i) -> {
                        this.itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + (i.getValorAdicional() * itemVenda.getQuantItemVenda()));
                    });
                }
                ItemVenda itemremover = null;
                for (ItemVenda v : carrinhoCompras) {

                    if (v.getProdutoItemVenda().getIdProduto().equals(itemVenda.getProdutoItemVenda().getIdProduto())) {
                        if (v.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida")) {
                            Double valor = v.getQuantItemVenda();
                            Double valornovo = itemVenda.getQuantItemVenda();
                            Double valorItem = v.getVlrItemVenda();
                            itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                            itemVenda.setQuantItemVenda(valornovo + valor);
                            itemremover = v;
                        } else if (v.getIngredientesAdicionais().equals(itemVenda.getIngredientesAdicionais()) && v.getIngredientesProduto().equals(itemVenda.getIngredientesProduto())) {
                            if (v.getVlrItemVenda().equals(valor)) {
                                Double valori = v.getQuantItemVenda();
                                Double valornovo = itemVenda.getQuantItemVenda();
                                Double valorItem = v.getVlrItemVenda();
                                itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                                itemVenda.setQuantItemVenda(valornovo + valori);
                                itemremover = v;
                            }
                        }
                    }
                }

                carrinhoCompras.remove(itemremover);
                itemVenda.setVlrUnitarioProduto(valor);
                itemVenda.setVendaItemVenda(vend.getIdVenda());
                this.carrinhoCompras.add(itemVenda);
                valor = null;
                calcularCarrinho();

                FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/home.jsf");
                this.itemVenda = new ItemVenda();
                itemVenda.setMetade(false);
            }
        } else {

            itemVenda.setProdutoItemVenda(prod);
            valor = tamanhoProduto.getValorTamanho();
            itemVenda.setTamanho(tamanhoProduto.getTamanho());
            if (itemVenda.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Pizza")) {
                if (itemVenda.getMetade()) {
                    itemVenda.setQuantItemVenda(0.5);
                    String aux = "";
                    for (int i = 0; i < itemVenda.getProdutoItemVenda().getTamanhos().size(); i++) {
                        if (itemVenda.getProdutoItemVenda().getTamanhos().get(i).getValorTamanho().equals(valor)) {
                            aux = itemVenda.getProdutoItemVenda().getTamanhos().get(i).getTamanho();
                        }
                    }
                    itemVenda.setTamanho(aux);
                }
            } else {
                itemVenda.setMetade(false);
            }
            this.itemVenda.setVendaItemVenda(vend.getIdVenda());

            this.itemVenda.setVlrItemVenda(itemVenda.getQuantItemVenda() * valor);
            if (itemVenda.getIngredientesAdicionais() != null) {
                itemVenda.getIngredientesAdicionais().stream().forEach((i) -> {
                    this.itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + (i.getValorAdicional() * itemVenda.getQuantItemVenda()));
                });
            }

            ItemVenda itemremover = null;
            for (ItemVenda v : carrinhoCompras) {

                if (v.getProdutoItemVenda().getIdProduto().equals(itemVenda.getProdutoItemVenda().getIdProduto())) {
                    if (v.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida")) {
                        Double valor = v.getQuantItemVenda();
                        Double valornovo = itemVenda.getQuantItemVenda();
                        Double valorItem = v.getVlrItemVenda();
                        itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                        itemVenda.setQuantItemVenda(valornovo + valor);
                        itemremover = v;
                    } else if (v.getIngredientesAdicionais().equals(itemVenda.getIngredientesAdicionais()) && v.getIngredientesProduto().equals(itemVenda.getIngredientesProduto())) {
                        if (v.getVlrItemVenda().equals(valor)) {
                            Double valor = v.getQuantItemVenda();
                            Double valornovo = itemVenda.getQuantItemVenda();
                            Double valorItem = v.getVlrItemVenda();
                            itemVenda.setVlrItemVenda(itemVenda.getVlrItemVenda() + valorItem);
                            itemVenda.setQuantItemVenda(valornovo + valor);
                            itemremover = v;
                        }
                    }
                }
            }

            carrinhoCompras.remove(itemremover);
            itemVenda.setVlrUnitarioProduto(valor);
            itemVenda.setVendaItemVenda(vend.getIdVenda());
            this.carrinhoCompras.add(itemVenda);
            valor = null;
            calcularCarrinho();

            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/home.jsf");
            this.itemVenda = new ItemVenda();
            itemVenda.setMetade(false);
        }
    }

    public void adicionarAvisoFlutuante(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                mensagem, "");
        context.addMessage("", msg);
    }

    public List<Venda> listarUltimasVendas(Long id) {
        VendaDAO vDAO = new VendaDAO();
        return vDAO.listar(id);
    }

    public void novasVendasMobile() {
        VendaDAO vendDAO = new VendaDAO();
        this.novasVendas = vendDAO.verificarNovasVendas();
    }

    public void carregar(Long id) {
        itemVenda = new ItemVenda();
        prod = pDAO.carregar(id);

        if (prod.getCategoriaProduto().getTituloCategoria().equals("Bebida")) {

            RequestContext.getCurrentInstance().execute("abreModalBebida();");
        } else if (prod.getCategoriaProduto().getTituloCategoria().equals("Pizza")) {

            RequestContext.getCurrentInstance().execute("abreModalPizza();");
        } else {

            RequestContext.getCurrentInstance().execute("abreModal();");
        }

    }

    public void carregarPreLoad() {
        
            prod = pDAO.carregar(idPreLoad);
    }

    public void carregarMobile(Long id) throws IOException {
        itemVenda = new ItemVenda();
        prod = pDAO.carregar(id);

        if (prod.getCategoriaProduto().getTituloCategoria().equals("Bebida")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/exibeProdutoBebida.jsf?idProduto=" + prod.getIdProduto());
            
        } else if (prod.getCategoriaProduto().getTituloCategoria().equals("Pizza")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/exibeProdutoPizza.jsf?idProduto=" + prod.getIdProduto());
            
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/exibeProdutoLanche.jsf?idProduto=" + prod.getIdProduto());
           
        }

    }

    public void calcularCarrinho() {
        this.valorTotal = 0.00;
        quantMetadeGrande = 0;
        quantMetadeMedia = 0;
        quantMetadePequena = 0;
        quantMetadeMini = 0;
        alcoolico = false;
        for (ItemVenda i : carrinhoCompras) {
            this.valorTotal += i.getVlrItemVenda();
            if (i.getProdutoItemVenda().getAlcoolico()) {
                alcoolico = true;
            }
            if (i.getMetade()) {
                if (i.getTamanho().equals("Grande")) {
                    quantMetadeGrande++;
                }
                if (i.getTamanho().equals("Media")) {
                    quantMetadeMedia++;
                }
                if (i.getTamanho().equals("Pequena")) {
                    quantMetadePequena++;
                }
                if (i.getTamanho().equals("Mini")) {
                    quantMetadeMini++;
                }
            }
        }
    }

    public void calcularCarrinhoEmpresa() {
        this.valorTotal = 0.00;
        carrinhoCompras.stream().forEach((v) -> {
            this.valorTotal += v.getVlrItemVenda();

        });
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public void limparCampos() {
        itemVenda = new ItemVenda();
        vend = new Venda();
        carrinhoCompras.clear();
        this.valorTotal = 0.00;
    }

    public void limparCamposEmpresa() {
        itemVenda = new ItemVenda();
        vend = new Venda();
        carrinhoCompras.clear();
        this.valorTotal = 0.00;
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public void removerCarrinho(ItemVenda itemVenda) {
        carrinhoCompras.remove(itemVenda);
        if (itemVenda.getMetade()) {
            if (itemVenda.getTamanho().equals("Grande")) {
                quantMetadeGrande--;
            }
            if (itemVenda.getTamanho().equals("Media")) {
                quantMetadeMedia--;
            }
            if (itemVenda.getTamanho().equals("Pequena")) {
                quantMetadePequena--;
            }
            if (itemVenda.getTamanho().equals("Mini")) {
                quantMetadeMini--;
            }
        }
        calcularCarrinho();
        RequestContext.getCurrentInstance().execute("estilo();");
    }

    public void finalizarVenda() throws IOException {
        VendaDAO vendDAO = new VendaDAO();
        if (endereco == null) {
            vend.setRetirada(Boolean.TRUE);
        } else {
            vend.setRetirada(Boolean.FALSE);
            vend.setEndereco(endereco);
            if (carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto().getValorEntrega() != null) {
                this.valorTotal += carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto().getValorEntrega();
            }
        }

        RequestContext.getCurrentInstance().execute("fechaModalCarrinho();");
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        System.out.println(alcoolico);
        if (getIdade(loginBean.getPessoaAtual().getDataNascPessoa()) < 18 && alcoolico) {
            adicionarAvisoFlutuante("Menores de idade não podem efetuar compras de produtos alcoólicos");
        } else if (quantMetadeGrande % 2 != 0 || quantMetadeMedia % 2 != 0 || quantMetadePequena % 2 != 0 || quantMetadeMini % 2 != 0) {
            adicionarAvisoFlutuante("Ao adicionar metade de uma pizza é necessário adicionar a outra metade");
        } else {
            vend.setDispositivo("Computador");
            vend.setCliente(loginBean.getPessoaAtual());
            vend.setVlrTotalVenda(valorTotal);
            vend.setCarrinho(carrinhoCompras);
            vend.setStatusVenda("Ativo");
            vend.setEmpresa(carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto());
            Date data = new Date();
            vend.setDataVenda(data);
            String mensagem = vendDAO.cadastrar(vend);
            this.limparCampos();
            adicionarAvisoFlutuante(mensagem);
            alcoolico = false;
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/home.jsf");
        }

    }

    public Integer getIdade(Date data) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(data);
        Calendar dataAtual = Calendar.getInstance();
        Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
        Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
        Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
        if (diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
            idade--;
        }
        System.out.println(idade);
        return idade;
    }

    public void adicionarModoEmpresa() {
        this.itemVenda.setVendaItemVenda(vend.getIdVenda());
        ItemVenda itemremover = null;
        for (ItemVenda v : carrinhoCompras) {
            if (v.getProdutoItemVenda().getIdProduto().equals(itemVenda.getProdutoItemVenda().getIdProduto())) {

                Double valor = v.getQuantItemVenda();
                Double valornovo = itemVenda.getQuantItemVenda();
                itemVenda.setQuantItemVenda(valornovo + valor);
                itemremover = v;

            }
        }
        carrinhoCompras.remove(itemremover);
        this.itemVenda.setVlrItemVenda(itemVenda.getQuantItemVenda() * itemVenda.getProdutoItemVenda().getValorProduto());

        this.carrinhoCompras.add(itemVenda);
        calcularCarrinho();
        this.itemVenda = new ItemVenda();
        RequestContext.getCurrentInstance().execute("estiloTabela();");

    }

    public void finalizarVendaModoEmpresa() throws IOException {
        VendaDAO vendDAO = new VendaDAO();
        if (endereco == null) {
            vend.setRetirada(Boolean.TRUE);
        } else {
            vend.setRetirada(Boolean.FALSE);
            vend.setEndereco(endereco);
        }
        RequestContext.getCurrentInstance().execute("fechaModalCarrinho();");
        vend.setVlrTotalVenda(valorTotal);
        vend.setCarrinho(carrinhoCompras);
        vend.setStatusVenda("Ativo");
        vend.setEmpresa(carrinhoCompras.get(0).getProdutoItemVenda().getEmpresaProduto());
        Date data = new Date();
        vend.setDataVenda(data);
        String mensagem = vendDAO.cadastrar(vend);
        this.limparCampos();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasEmpresa/listaVenda.xhtml");
    }

    public void proceguirVenda() throws IOException {
        if (!carrinhoCompras.isEmpty()) {
            FacesContext c = FacesContext.getCurrentInstance();
            ELResolver r = c.getApplication().getELResolver();
            LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
            if (loginBean.isLoggedUsuario()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/finalizaVenda.jsf");
            } else {
                adicionarAvisoFlutuante("Você deve estar logado primeiro");
            }
        } else {
            adicionarAvisoFlutuante("O carrinho de compras está vazio");
        }
    }

    public void cancelarVenda(Venda v) {
        v.setStatusVenda("Cancelada");
        VendaDAO vendDAO = new VendaDAO();
        vendDAO.atualizar(v);
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public void finalizarVenda(Venda v) {
        v.setStatusVenda("Finalizado");
        VendaDAO vendDAO = new VendaDAO();
        vendDAO.atualizarMobile(v);
        RequestContext.getCurrentInstance().execute("estiloTabela();");
    }

    public void imprimirFicha(Venda venda) {

        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
            session.setAttribute("caminho", "fichaVenda.jasper");
            session.setAttribute("tipo", "fichavenda");
            session.setAttribute("venda", venda.getIdVenda());
            util.Util.redirect("/ExemploRelatorioServlet");

        } catch (ExceptionInInitializerError ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

    public ItemVenda getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    public Venda getVend() {
        return vend;
    }

    public void setVend(Venda vend) {
        this.vend = vend;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<ItemVenda> getCarrinhoCompras() {
        return carrinhoCompras;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void novasVendas() {
        VendaDAO vendDAO = new VendaDAO();
        this.novasVendas = vendDAO.verificarNovasVendas();
    }

    public String getNovasVendas() {
        return novasVendas;
    }

    public void setNovasVendas(String novasVendas) {
        this.novasVendas = novasVendas;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public VendaBean() {
        init();
    }

    public String getUpdateForm() {
        return updateForm;
    }

    public void setUpdateForm(String updateForm) {
        this.updateForm = updateForm;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Tamanho getTamanhoProduto() {
        return tamanhoProduto;
    }

    public void setTamanhoProduto(Tamanho tamanhoProduto) {
        this.tamanhoProduto = tamanhoProduto;
    }

    public Long getIdPreLoad() {
        return idPreLoad;
    }

    public void setIdPreLoad(Long idPreLoad) {
        this.idPreLoad = idPreLoad;
    }

}
