package service;

import dao.IngredienteDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Ingrediente;
import model.Produto;

@Path("/produto")
public class ProdutoService {

    
    @GET
    @Path("porempresa")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ArrayList listarProdutoPorEmpresa(@QueryParam("empresa") Integer id, @QueryParam("quant") Integer quant, @QueryParam("total") Integer total) {
        ProdutoDAO dao = new ProdutoDAO();
        return (ArrayList) dao.listar(id, quant, total);
    }

    @GET
    @Path("produtosEmpresa")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ArrayList produtosEmpresa(@QueryParam("empresa") Integer id) {
        ProdutoDAO dao = new ProdutoDAO();
        return (ArrayList) dao.listarProdutosEmpresa(id);
    }
    
    @GET
    @Path("statusProduto")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String alterarStatusProduto(@QueryParam("idProduto") Integer id) {
        ProdutoDAO dao = new ProdutoDAO();
        Produto p = dao.carregar(Long.parseLong(id.toString()));
        if(p.getStatusProduto().equals("Ativo")){
            p.setStatusProduto("Inativo");
        }
        else{
            p.setStatusProduto("Ativo");
        }
        return dao.atualizar(p);
    }

    @GET
    @Path("porempresaCategoria")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ArrayList listarProdutoPorEmpresaCategoria(@QueryParam("empresa") Integer id, @QueryParam("quant") Integer quant, @QueryParam("total") Integer total, @QueryParam("categoria") String categoria) {
        ProdutoDAO dao = new ProdutoDAO();
        return (ArrayList) dao.listar(id, quant, total, categoria);
    }

    
    @GET
    @Path("porid")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Produto listarProdutoPorId(@QueryParam("idProduto") Long id) {
        ProdutoDAO dao = new ProdutoDAO();
        return dao.carregar(id);
    }

    @GET
    @Path("ingredientes")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Ingrediente> listaIngredientesAdicionais(@QueryParam("idEmpresa") Long id) {
        IngredienteDAO iDAO = new IngredienteDAO();
        return iDAO.listarAdicional(id);
    }

}
