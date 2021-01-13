package controller;

import dao.EnderecoDAO;
import dao.PessoaDAO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Categoria;
import model.Endereco;
import model.FormaPagamento;
import model.Pessoa;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import util.UploadArquivo;

/**
 * <p>
 * PessoaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class PessoaBean {

    private Pessoa pessoa;
    private ArrayList<Endereco> enderecos;
    private Endereco endereco;
    private PessoaDAO pDAO;
    private String senha;
    private String confirmasenha;
    private String tipoS;
    private Boolean tipo;
    protected String id;
    private EnderecoDAO eDAO;
    private ArrayList<Categoria> categorias;
    private ArrayList<FormaPagamento> formas;
    private Boolean termos;
    private String DataNascimento = null;

    @PostConstruct
    private void init() {
        endereco = new Endereco();
        pessoa = new Pessoa();
        enderecos = new ArrayList();
        pDAO = new PessoaDAO();
        eDAO = new EnderecoDAO();
        categorias = new ArrayList();
        formas = new ArrayList();

    }

    public void salvar() throws ParseException, IOException {
        pessoa.setTipoPessoa("Cliente");

        //verifica se os campos estão digitados
        if ("".equals(senha)
                || "".equals(confirmasenha)
                || "".equals(pessoa.getCelularPessoa())
                || "".equals(pessoa.getEmailPessoa())
                || "".equals(pessoa.getDataNascPessoa())               
                || "".equals(pessoa.getTipoPessoa())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } //verifica se a senha está confirmada        
        else if (!senha.equals(confirmasenha)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("As senhas digitadas estão diferentes!"));

        } else if (!termos) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Você deve concordar com os Termos e Condições de uso!"));
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(DataNascimento);
            pessoa.setDataNascPessoa(parsed);
            pessoa.setStatusPessoa("Ativo");
            /* endereco.setPessoa(pessoa.getIdPessoa());
            enderecos.add(endereco);*/
            pessoa.setEnderecos(null);
            pessoa.setSenhaPessoa(senha);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(pDAO.cadastrar(pessoa)));
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/home.jsf");

        }

    }

    public void adicionarEndereco(Long id) {
        if ("".equals(endereco.getRuaEndereco())
                || "".equals(endereco.getNumeroEndereco())
                || "".equals(endereco.getEstadoEndereco())
                || "".equals(endereco.getBairroEndereco())
                || "".equals(endereco.getCidadeEndereco())
                || "".equals(endereco.getCepEndereco())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else {
            endereco.setStatus("Ativo");
            endereco.setPessoa(id);            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(eDAO.atualizar(endereco))); 
            pessoa = new Pessoa();
            recarregaDadosLogin();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/admCliente.jsf");
            } catch (IOException ex) {
                Logger.getLogger(PessoaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

    }

    public void adicionarEnderecoModoEmpresa(Long id) {
        if ("".equals(endereco.getRuaEndereco())
                || "".equals(endereco.getNumeroEndereco())
                || "".equals(endereco.getEstadoEndereco())
                || "".equals(endereco.getBairroEndereco())
                || "".equals(endereco.getCidadeEndereco())
                || "".equals(endereco.getCepEndereco())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } else  {
            endereco.setStatus("Ativo");
            endereco.setPessoa(id);            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(eDAO.atualizar(endereco))); 
            recarregaDadosLogin();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasEmpresa/admEmpresa.jsf");
            } catch (IOException ex) {
                Logger.getLogger(PessoaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

    }

    public void recarregaDadosLogin() {
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        loginBean.recarregarLogin();
    }

    public void novoEndereco() {
        RequestContext.getCurrentInstance().execute("abreModalEndereco();");
        endereco = null;
        endereco = new Endereco();

    }

    public void atualizarPessoa() throws IOException, ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date parsed = format.parse(DataNascimento);
        pessoa.setDataNascPessoa(parsed);
        pessoa.setSenhaPessoa(senha);
        context.addMessage(null, new FacesMessage(pDAO.atualizar(pessoa)));
        recarregaDadosLogin();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/admCliente.jsf");
    }

    public void atualizarPessoaModoEmpresa() throws IOException {
        if(!senha.equals(confirmasenha)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("As senhas digitadas estão diferentes"));
        }
        else{
            pessoa.setListaFormaPagamento(formas);
            pessoa.setSenhaPessoa(senha);
            pessoa.setCategorias(categorias);       
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(pDAO.atualizar(pessoa)));
            recarregaDadosLogin();
            FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasEmpresa/admEmpresa.jsf");
        }
    }

    public void salvarEmpresa() {
        System.out.println("Tipo: " + tipo);
        pessoa.setTipoPessoa("Empresa");
        //verifica se os campos estão digitados
        if ("".equals(senha)
                || "".equals(confirmasenha)
                || "".equals(pessoa.getCpfCnpjPessoa())
                || "".equals(pessoa.getEmailPessoa())
                || "".equals(pessoa.getTipoPessoa())
                || "".equals(endereco.getCepEndereco())
                || "".equals(endereco.getCidadeEndereco())
                || "".equals(endereco.getComplementoEndereco())
                || "".equals(endereco.getEstadoEndereco())
                || "".equals(endereco.getNumeroEndereco())
                || "".equals(endereco.getRuaEndereco())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Preencha todos os campos!"));
        } //verifica se a senha está confirmada        
        else if (!senha.equals(confirmasenha)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("As senhas digitadas estão diferentes!"));

        } else {
            pessoa.setStatusPessoa("Ativo");
            // endereco.setPessoa(pessoa);
            enderecos.add(endereco);
            pessoa.setEnderecos(enderecos);
            pessoa.setSenhaPessoa(senha);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(pDAO.cadastrar(pessoa)));
            endereco = null;
        }
    }

    public void atualizarEmpresa() {
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        pessoa = loginBean.getPessoaAtual();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(pDAO.atualizar(pessoa)));
        recarregaDadosLogin();
    }

    public void alterarStatus(Pessoa p) {
        if ("Ativo".equals(p.getStatusPessoa())) {
            p.setStatusPessoa("Inativo");
        } else if ("Inativo".equals(p.getStatusPessoa())) {
            p.setStatusPessoa("Ativo");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(pDAO.atualizar(p)));
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmasenha() {
        return confirmasenha;
    }

    public void setConfirmasenha(String confirmasenha) {
        this.confirmasenha = confirmasenha;
    }

    public PessoaBean() {
        init();
    }

    public String getTipoS() {
        return tipoS;
    }

    public void setTipoS(String tipoS) {
        this.tipoS = tipoS;
        if ("F".equals(tipoS)) {
            setTipo(true);
        } else if ("J".equals(tipoS)) {
            setTipo(false);
        }
        RequestContext.getCurrentInstance().execute("refazMascara();");
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void upload(FileUploadEvent event) {

        if (event != null) {
            try {
                Date data = new Date();
                pessoa.setImagem(data.getTime() + ".png");
                UploadArquivo up = new UploadArquivo();
                up.copyFile(pessoa.getImagem(), event.getFile().getInputstream());
                event = new FileUploadEvent(null, null);
            } catch (Exception ex) {
                System.out.println("Erro ao fazer upload: " + ex.getMessage());
            }
        }
    }

    public void carregar() {       
            if (id != null && pessoa.getIdPessoa() == null) {
                FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("idEmpresa", id);
                pessoa = pDAO.carregar(Long.parseLong(id));
            }
    }

    public void carregar(Long id) {
        pessoa = pDAO.carregar(id);
        for (Categoria c : pessoa.getCategorias()) {
            categorias.add(c);
        }        
        for (FormaPagamento f : pessoa.getListaFormaPagamento()) {
            formas.add(f);
        }
        System.out.println("Tamanho: "+formas.size());
        if (pessoa.getDataNascPessoa() != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            DataNascimento = df.format(pessoa.getDataNascPessoa());
        }
        RequestContext.getCurrentInstance().execute("abreModalCliente();");
    }
    public void carregarMobile(Long id) {
        pessoa = pDAO.carregar(id);
        for (Categoria c : pessoa.getCategorias()) {
            categorias.add(c);
        }        
        for (FormaPagamento f : pessoa.getListaFormaPagamento()) {
            formas.add(f);
            System.out.println(f.getFormaPagamento());
        }
        if (pessoa.getDataNascPessoa() != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            DataNascimento = df.format(pessoa.getDataNascPessoa());
        }
    }

    public void carregarEndereco(Endereco c) {
        this.endereco = eDAO.carregar(c.getIdEndereco());
        RequestContext.getCurrentInstance().execute("abreModalEndereco();");

    }

    public void removerEndereco(Endereco e) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(eDAO.deletar(e)));
        recarregaDadosLogin();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../paginasUsuario/admCliente.jsf");
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public ArrayList<FormaPagamento> getFormas() {
        return formas;
    }

    public void setFormas(ArrayList<FormaPagamento> formas) {
        this.formas = formas;
    }

    
    public Boolean getTermos() {
        return termos;
    }

    public void setTermos(Boolean termos) {
        this.termos = termos;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String DataNascimento) {
        this.DataNascimento = DataNascimento;
    }

}
