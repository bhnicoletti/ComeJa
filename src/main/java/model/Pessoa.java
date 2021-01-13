/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@XmlRootElement
@Entity
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPessoa;
    private String nomeFantasiaPessoa;
    private String imagem;
    private String razaoSocialPessoa;
    private String senhaPessoa;
    private String telefonePessoa;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascPessoa;
    private String celularPessoa;
    @Column(unique = true)
    private String emailPessoa;
    private String statusPessoa;
    private String tipoPessoa;
    @Column(unique = true)
    private String cpfCnpjPessoa;
    private String rgIePessoa;
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Endereco> enderecos;
    private String categoriaPessoa;
    private String tempoPreparo;
    private Double valorEntrega;
    @JoinTable(name = "categoriasEmpresa",
            joinColumns = {
                @JoinColumn(name = "idPessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "idCategoria")})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Categoria> categorias;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cidade cidade;
    private String horarioFuncionamento;
    private String obs;
     @JoinTable(name = "listaformasPagamentoEmpresa",
            joinColumns = {
                @JoinColumn(name = "idPessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "idFormaPagamento")})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<FormaPagamento> listaFormaPagamento;

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomeFantasiaPessoa() {
        return nomeFantasiaPessoa;
    }

    public void setNomeFantasiaPessoa(String nomeFantasiaPessoa) {
        this.nomeFantasiaPessoa = nomeFantasiaPessoa;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getRazaoSocialPessoa() {
        return razaoSocialPessoa;
    }

    public void setRazaoSocialPessoa(String razaoSocialPessoa) {
        this.razaoSocialPessoa = razaoSocialPessoa;
    }

    public String getSenhaPessoa() {
        return senhaPessoa;
    }

    public void setSenhaPessoa(String senhaPessoa) {        
        this.senhaPessoa = util.Criptografia.criptografar(senhaPessoa);
    }

    public String getTelefonePessoa() {
        return telefonePessoa;
    }

    public void setTelefonePessoa(String telefonePessoa) {
        this.telefonePessoa = telefonePessoa;
    }

    public Date getDataNascPessoa() {
        return dataNascPessoa;
    }

    public void setDataNascPessoa(Date dataNascPessoa) {
        this.dataNascPessoa = dataNascPessoa;
    }

    public String getCelularPessoa() {
        return celularPessoa;
    }

    public void setCelularPessoa(String celularPessoa) {
        this.celularPessoa = celularPessoa;
    }

    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public String getStatusPessoa() {
        return statusPessoa;
    }

    public void setStatusPessoa(String statusPessoa) {
        this.statusPessoa = statusPessoa;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfCnpjPessoa() {
        return cpfCnpjPessoa;
    }

    public void setCpfCnpjPessoa(String cpfCnpjPessoa) {
        this.cpfCnpjPessoa = cpfCnpjPessoa;
    }

    public String getRgIePessoa() {
        return rgIePessoa;
    }

    public void setRgIePessoa(String rgIePessoa) {
        this.rgIePessoa = rgIePessoa;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public String getCategoriaPessoa() {
        return categoriaPessoa;
    }

    public void setCategoriaPessoa(String categoriaPessoa) {
        this.categoriaPessoa = categoriaPessoa;
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public Double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(Double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<FormaPagamento> getListaFormaPagamento() {
        return listaFormaPagamento;
    }

    public void setListaFormaPagamento(List<FormaPagamento> listaFormaPagamento) {
        this.listaFormaPagamento = listaFormaPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idPessoa);
        hash = 71 * hash + Objects.hashCode(this.nomeFantasiaPessoa);
        hash = 71 * hash + Objects.hashCode(this.imagem);
        hash = 71 * hash + Objects.hashCode(this.razaoSocialPessoa);
        hash = 71 * hash + Objects.hashCode(this.senhaPessoa);
        hash = 71 * hash + Objects.hashCode(this.telefonePessoa);
        hash = 71 * hash + Objects.hashCode(this.dataNascPessoa);
        hash = 71 * hash + Objects.hashCode(this.celularPessoa);
        hash = 71 * hash + Objects.hashCode(this.emailPessoa);
        hash = 71 * hash + Objects.hashCode(this.statusPessoa);
        hash = 71 * hash + Objects.hashCode(this.tipoPessoa);
        hash = 71 * hash + Objects.hashCode(this.cpfCnpjPessoa);
        hash = 71 * hash + Objects.hashCode(this.rgIePessoa);
        hash = 71 * hash + Objects.hashCode(this.enderecos);
        hash = 71 * hash + Objects.hashCode(this.categoriaPessoa);
        hash = 71 * hash + Objects.hashCode(this.tempoPreparo);
        hash = 71 * hash + Objects.hashCode(this.valorEntrega);
        hash = 71 * hash + Objects.hashCode(this.categorias);
        hash = 71 * hash + Objects.hashCode(this.cidade);
        hash = 71 * hash + Objects.hashCode(this.horarioFuncionamento);
        hash = 71 * hash + Objects.hashCode(this.obs);
        hash = 71 * hash + Objects.hashCode(this.listaFormaPagamento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.nomeFantasiaPessoa, other.nomeFantasiaPessoa)) {
            return false;
        }
        if (!Objects.equals(this.imagem, other.imagem)) {
            return false;
        }
        if (!Objects.equals(this.razaoSocialPessoa, other.razaoSocialPessoa)) {
            return false;
        }
        if (!Objects.equals(this.senhaPessoa, other.senhaPessoa)) {
            return false;
        }
        if (!Objects.equals(this.telefonePessoa, other.telefonePessoa)) {
            return false;
        }
        if (!Objects.equals(this.celularPessoa, other.celularPessoa)) {
            return false;
        }
        if (!Objects.equals(this.emailPessoa, other.emailPessoa)) {
            return false;
        }
        if (!Objects.equals(this.statusPessoa, other.statusPessoa)) {
            return false;
        }
        if (!Objects.equals(this.tipoPessoa, other.tipoPessoa)) {
            return false;
        }
        if (!Objects.equals(this.cpfCnpjPessoa, other.cpfCnpjPessoa)) {
            return false;
        }
        if (!Objects.equals(this.rgIePessoa, other.rgIePessoa)) {
            return false;
        }
        if (!Objects.equals(this.categoriaPessoa, other.categoriaPessoa)) {
            return false;
        }
        if (!Objects.equals(this.tempoPreparo, other.tempoPreparo)) {
            return false;
        }
        if (!Objects.equals(this.horarioFuncionamento, other.horarioFuncionamento)) {
            return false;
        }
        if (!Objects.equals(this.obs, other.obs)) {
            return false;
        }
        if (!Objects.equals(this.idPessoa, other.idPessoa)) {
            return false;
        }
        if (!Objects.equals(this.dataNascPessoa, other.dataNascPessoa)) {
            return false;
        }
        if (!Objects.equals(this.enderecos, other.enderecos)) {
            return false;
        }
        if (!Objects.equals(this.valorEntrega, other.valorEntrega)) {
            return false;
        }
        if (!Objects.equals(this.categorias, other.categorias)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.listaFormaPagamento, other.listaFormaPagamento)) {
            return false;
        }
        return true;
    }

    

    

}
