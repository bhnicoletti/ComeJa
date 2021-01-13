/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listagem;

import controller.LoginBean;
import dao.PessoaDAO;
import dao.ProdutoDAO;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONArray;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Nicoletti
 */
@ManagedBean
@ViewScoped
public class GraficosBean {

    private ProdutoDAO pDAO = new ProdutoDAO();
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public PieChartModel produtosMaisVendidos() {
        PieChartModel pieModel = new PieChartModel();
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        JSONArray list = new JSONArray(pDAO.listaProdutosMaisVendidosPorEmpresa(loginBean.getPessoaAtual().getIdPessoa()));
        for (int i = 0; i < list.length(); i++) {
            pieModel.set(list.getJSONArray(i).getString(0), list.getJSONArray(i).getDouble(1));
        }
        System.out.println(pieModel.getData());
        pieModel.setTitle("Produtos Mais Vendidos");
        pieModel.setLegendPosition("w");
        return pieModel;
    }

    public LineChartModel vendasNoMes() {
        LineChartModel model = new LineChartModel();
        FacesContext c = FacesContext.getCurrentInstance();
        ELResolver r = c.getApplication().getELResolver();
        LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
        JSONArray list = new JSONArray(pessoaDAO.listaVendaMes(loginBean.getPessoaAtual().getIdPessoa()));
        String[] mes = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        LineChartSeries  serie = new LineChartSeries();
        serie.setLabel("Linha");
        for (int i = 0; i < list.length(); i++) {
            Integer posicao = list.getJSONArray(i).getInt(0);           
            serie.set(mes[posicao - 1], list.getJSONArray(i).getInt(1));           
        }
        

        model.addSeries(serie);
        model.setTitle("Vendas Por Mês");
        model.getAxis(AxisType.Y).setLabel("Quantidade de Vendas");
        CategoryAxis axis = new CategoryAxis("Meses");
        model.getAxes().put(AxisType.X, axis);
        return model;
    }

}
