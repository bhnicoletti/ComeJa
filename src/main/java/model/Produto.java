/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.JoinFetch;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private Double valorProduto;
    private String imagemProduto;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pessoa empresaProduto;
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoriaProduto;
    @ManyToMany(cascade = CascadeType.ALL) 
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private List<Ingrediente> ingredientesProduto;    
    private String statusProduto;
    @OneToMany(cascade = CascadeType.ALL) 
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private List<Tamanho> tamanhos;
    private Boolean alcoolico;

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(String imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public Pessoa getEmpresaProduto() {
        return empresaProduto;
    }

    public void setEmpresaProduto(Pessoa empresaProduto) {
        this.empresaProduto = empresaProduto;
    }

    public Categoria getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(Categoria categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public List<Ingrediente> getIngredientesProduto() {
        return ingredientesProduto;
    }

    public void setIngredientesProduto(List<Ingrediente> ingredientesProduto) {
        this.ingredientesProduto = ingredientesProduto;
    }

    public String getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(String statusProduto) {
        this.statusProduto = statusProduto;
    }

    public List<Tamanho> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<Tamanho> tamanhos) {
        this.tamanhos = tamanhos;
    }

    public Boolean getAlcoolico() {
        return alcoolico;
    }

    public void setAlcoolico(Boolean alcoolico) {
        this.alcoolico = alcoolico;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idProduto);
        hash = 41 * hash + Objects.hashCode(this.nomeProduto);
        hash = 41 * hash + Objects.hashCode(this.descricaoProduto);
        hash = 41 * hash + Objects.hashCode(this.valorProduto);
        hash = 41 * hash + Objects.hashCode(this.imagemProduto);
        hash = 41 * hash + Objects.hashCode(this.empresaProduto);
        hash = 41 * hash + Objects.hashCode(this.categoriaProduto);
        hash = 41 * hash + Objects.hashCode(this.ingredientesProduto);
        hash = 41 * hash + Objects.hashCode(this.statusProduto);
        hash = 41 * hash + Objects.hashCode(this.tamanhos);
        hash = 41 * hash + Objects.hashCode(this.alcoolico);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.nomeProduto, other.nomeProduto)) {
            return false;
        }
        if (!Objects.equals(this.descricaoProduto, other.descricaoProduto)) {
            return false;
        }
        if (!Objects.equals(this.imagemProduto, other.imagemProduto)) {
            return false;
        }
        if (!Objects.equals(this.statusProduto, other.statusProduto)) {
            return false;
        }
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        if (!Objects.equals(this.valorProduto, other.valorProduto)) {
            return false;
        }
        if (!Objects.equals(this.empresaProduto, other.empresaProduto)) {
            return false;
        }
        if (!Objects.equals(this.categoriaProduto, other.categoriaProduto)) {
            return false;
        }
        if (!Objects.equals(this.ingredientesProduto, other.ingredientesProduto)) {
            return false;
        }
        if (!Objects.equals(this.tamanhos, other.tamanhos)) {
            return false;
        }
        if (!Objects.equals(this.alcoolico, other.alcoolico)) {
            return false;
        }
        return true;
    }

    public Produto() {
    }
    
    

}
