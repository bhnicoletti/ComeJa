/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.VendaDAO;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Venda;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;


@Path("/venda")
public class VendaService {

    
    @GET
    @Path("ultimascompras")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String listaVenda(@QueryParam("idCliente") Long cliente) {
        VendaDAO vendDAO = new VendaDAO();
        ArrayList lista = (ArrayList) vendDAO.listarMobile(cliente);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("ultimasvendas")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String listaVendaEmpresa(@QueryParam("idEmpresa") Long empresa) {
        VendaDAO vendDAO = new VendaDAO();
        ArrayList lista = (ArrayList) vendDAO.listarMobileEmpresa(empresa);
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @GET
    @Path("finalizarVenda")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String finalizarVenda(@QueryParam("idVenda") Long venda) {
        VendaDAO vendDAO = new VendaDAO();

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        Venda v = vendDAO.carregar(venda);
        v.setStatusVenda("Finalizado");

        if (vendDAO.atualizarMobile(v)) {
            array.put("Sucesso");
        } else {
            array.put("Erro");
        }
        json.put("resposta", array);
        return json.toString();
    }

    @GET
    @Path("aceitarVenda")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String aceitarVenda(@QueryParam("idVenda") Long venda) {
        VendaDAO vendDAO = new VendaDAO();

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        Venda v = vendDAO.carregar(venda);
        v.setStatusVenda("Aceito");

        if (vendDAO.atualizarMobile(v)) {
            array.put("Sucesso");
        } else {
            array.put("Erro");
        }
        json.put("resposta", array);
        return json.toString();
    }

    @GET
    @Path("recusarVenda")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String recusarVenda(@QueryParam("idVenda") Long venda) {
        VendaDAO vendDAO = new VendaDAO();

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        Venda v = vendDAO.carregar(venda);
        v.setStatusVenda("Recusado");

        if (vendDAO.atualizarMobile(v)) {
            array.put("Sucesso");
        } else {
            array.put("Erro");
        }
        json.put("resposta", array);
        return json.toString();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/realizarvenda")
    public String realizarVenda(String string) {

        System.out.println(string);
        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd")
                                .create();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(string).getAsJsonObject();
        Venda venda = gson.fromJson(o, Venda.class);
        Date data = new Date();
        venda.setDataVenda(data);
        VendaDAO vendDAO = new VendaDAO();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        if (vendDAO.cadastrarVendaMobile(venda)) {
            array.put("Sucesso");
        } else {
            array.put("Erro");
        }

        json.put("resposta", array);
        System.out.println(json);
        return json.toString();
    }

    @GET
    @Path("porid")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Venda listarProdutoPorId(@QueryParam("idVenda") Long id) {
        VendaDAO dao = new VendaDAO();
        return dao.carregar(id);
    }

}
