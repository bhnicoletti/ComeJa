/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.ProdutoDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Produto;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.Filtro;


@ManagedBean
@ViewScoped
public class ListaProdutoBean implements Serializable {  
  
    private static final long serialVersionUID = 1L; 
    
    private Filtro filtro = new Filtro();
    private LazyDataModel<Produto> model;
    private final ProdutoDAO prodDAO = new ProdutoDAO();

   
    public Filtro getFiltro() {
        return filtro;
    }

  
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public LazyDataModel<Produto> getModel() {
        return model;
    }

  
    public void setModel(LazyDataModel<Produto> model) {
        this.model = model;        
    }

 
    public ListaProdutoBean() {
        model = new LazyDataModel<Produto>() {

            @Override
            public List<Produto> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
                filtro.setPropriedadeOrdenacao("nomeProduto");

                setRowCount(prodDAO.quantidadeFiltrados(filtro));
                RequestContext.getCurrentInstance().execute("estilo();");
                return prodDAO.filtrados(filtro);
                
            }

        };
    }

}
