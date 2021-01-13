/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@XmlRootElement
@Entity
public class ItemVenda implements Serializable {

    @Id
    @GeneratedValue
    private Long idItemVenda;
    private Double quantItemVenda;
    @ManyToOne
    private Produto produtoItemVenda;
    private Double vlrItemVenda; 
    private Double vlrUnitarioProduto;
    private Long vendaItemVenda;
    @JoinTable(name = "ingredientesProduto",
            joinColumns = {
                @JoinColumn(name = "idItemVenda")},
            inverseJoinColumns = {
                @JoinColumn(name = "idIngrediente")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Ingrediente> ingredientesProduto;
    @JoinTable(name = "ingredientesRetirados",
            joinColumns = {
                @JoinColumn(name = "idItemVenda")},
            inverseJoinColumns = {
                @JoinColumn(name = "idIngrediente")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Ingrediente> ingredientesRetirados;
    @JoinTable(name = "ingredientesAdicionais",
            joinColumns = {
                @JoinColumn(name = "idItemVenda")},
            inverseJoinColumns = {
                @JoinColumn(name = "idIngrediente")})
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Ingrediente> ingredientesAdicionais;
    private Boolean metade = false;
    private String tamanho;

    public Long getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(Long idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public Double getQuantItemVenda() {
        return quantItemVenda;
    }

    public void setQuantItemVenda(Double quantItemVenda) {
        this.quantItemVenda = quantItemVenda;
    }

    public Produto getProdutoItemVenda() {
        return produtoItemVenda;
    }

    public void setProdutoItemVenda(Produto produtoItemVenda) {
        this.produtoItemVenda = produtoItemVenda;
    }

    public Double getVlrItemVenda() {
        return vlrItemVenda;
    }

    public void setVlrItemVenda(Double vlrItemVenda) {
        this.vlrItemVenda = vlrItemVenda;
    }

    public Double getVlrUnitarioProduto() {
        return vlrUnitarioProduto;
    }

    public void setVlrUnitarioProduto(Double vlrUnitarioProduto) {
        this.vlrUnitarioProduto = vlrUnitarioProduto;
    }

    public Long getVendaItemVenda() {
        return vendaItemVenda;
    }

    public void setVendaItemVenda(Long vendaItemVenda) {
        this.vendaItemVenda = vendaItemVenda;
    }

    public List<Ingrediente> getIngredientesProduto() {
        return ingredientesProduto;
    }

    public void setIngredientesProduto(List<Ingrediente> ingredientesProduto) {
        this.ingredientesProduto = ingredientesProduto;
    }

    public List<Ingrediente> getIngredientesRetirados() {
        return ingredientesRetirados;
    }

    public void setIngredientesRetirados(List<Ingrediente> ingredientesRetirados) {
        this.ingredientesRetirados = ingredientesRetirados;
    }

    public List<Ingrediente> getIngredientesAdicionais() {
        return ingredientesAdicionais;
    }

    public void setIngredientesAdicionais(List<Ingrediente> ingredientesAdicionais) {
        this.ingredientesAdicionais = ingredientesAdicionais;
    }

    public Boolean getMetade() {
        return metade;
    }

    public void setMetade(Boolean metade) {
        this.metade = metade;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

   
    

}
