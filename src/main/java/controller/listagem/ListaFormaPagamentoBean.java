/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.FormaPagamentoDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.FormaPagamento;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import util.Filtro;

@ManagedBean
@ViewScoped
public class ListaFormaPagamentoBean implements Serializable {  
  
    private static final long serialVersionUID = 1L; 
    
    private Filtro filtro = new Filtro();
    private LazyDataModel<FormaPagamento> model;
    private final FormaPagamentoDAO cDAO = new FormaPagamentoDAO();


    public Filtro getFiltro() {
        return filtro;
    }


    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public LazyDataModel<FormaPagamento> getModel() {
        return model;
    }

  
    public void setModel(LazyDataModel<FormaPagamento> model) {
        this.model = model;        
    }

    public ListaFormaPagamentoBean() {
        model = new LazyDataModel<FormaPagamento>() {

            @Override
            public List<FormaPagamento> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
                filtro.setPropriedadeOrdenacao("formaPagamento");

                setRowCount(cDAO.quantidadeFiltrados(filtro));
                RequestContext.getCurrentInstance().execute("estilo();");
                return cDAO.filtrados(filtro);
                
            }

        };
    }

}
