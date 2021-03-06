/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.VendaDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Venda;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.Filtro;

/**
 * <p>ListaVendaBean class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@ManagedBean
@ViewScoped
public class ListaVendaBean implements Serializable {  
  
    private static final long serialVersionUID = 1L; 
    
    private Filtro filtro = new Filtro();
    private LazyDataModel<Venda> model;
    private final VendaDAO vendaDAO = new VendaDAO();

   
    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

  
    public LazyDataModel<Venda> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Venda> model) {
        this.model = model;
    }

    public ListaVendaBean() {
        model = new LazyDataModel<Venda>() {

            @Override
            public List<Venda> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
                filtro.setPropriedadeOrdenacao(sortField);

                setRowCount(vendaDAO.quantidadeFiltrados(filtro));

                return vendaDAO.filtrados(filtro);
            }

        };
    }

}
