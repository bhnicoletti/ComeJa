/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import dao.ConfiguracaoDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Configuracao;
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
public class ListaConfiguracaoBean implements Serializable {  
  
    private static final long serialVersionUID = 1L; 
    
    private Filtro filtro = new Filtro();
    private LazyDataModel<Configuracao> model;
    private final ConfiguracaoDAO configDAO = new ConfiguracaoDAO();

    public Filtro getFiltro() {
        return filtro;
    }

  
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

   
    public LazyDataModel<Configuracao> getModel() {
        return model;
    }

    
    public void setModel(LazyDataModel<Configuracao> model) {
        this.model = model;        
    }

   
    public ListaConfiguracaoBean() {
        model = new LazyDataModel<Configuracao>() {

            @Override
            public List<Configuracao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setAscendente(SortOrder.DESCENDING.equals(sortOrder));

                setRowCount(configDAO.quantidadeFiltrados(filtro));
                RequestContext.getCurrentInstance().execute("estilo();");
                return configDAO.filtrados(filtro);
                
            }

        };
    }

}
