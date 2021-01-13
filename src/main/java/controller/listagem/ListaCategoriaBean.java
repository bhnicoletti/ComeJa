/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.CategoriaDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Categoria;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.Filtro;

/**
 * <p>ListaCategoriaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class ListaCategoriaBean implements Serializable {  
  
    private static final long serialVersionUID = 1L; 
    
    private Filtro filtro = new Filtro();
    private LazyDataModel<Categoria> model;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * <p>Getter for the field <code>filtro</code>.</p>
     *
     * @return a {@link util.Filtro} object.
     */
    public Filtro getFiltro() {
        return filtro;
    }

    /**
     * <p>Setter for the field <code>filtro</code>.</p>
     *
     * @param filtro a {@link util.Filtro} object.
     */
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    /**
     * <p>Getter for the field <code>model</code>.</p>
     *
     * @return a {@link org.primefaces.model.LazyDataModel} object.
     */
    public LazyDataModel<Categoria> getModel() {
        return model;
    }

    /**
     * <p>Setter for the field <code>model</code>.</p>
     *
     * @param model a {@link org.primefaces.model.LazyDataModel} object.
     */
    public void setModel(LazyDataModel<Categoria> model) {
        this.model = model;        
    }

    /**
     * <p>Constructor for ListaCategoriaBean.</p>
     */
    public ListaCategoriaBean() {
        model = new LazyDataModel<Categoria>() {

            @Override
            public List<Categoria> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
                filtro.setPropriedadeOrdenacao("tituloCategoria");

                setRowCount(categoriaDAO.quantidadeFiltrados(filtro));
                RequestContext.getCurrentInstance().execute("estilo();");
                return categoriaDAO.filtrados(filtro);
                
            }

        };
    }

}
