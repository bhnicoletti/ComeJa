package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.CidadeDAO;
import dao.ConfiguracaoDAO;
import dao.DispositivoDAO;
import dao.EnderecoDAO;
import dao.LoginDAO;
import dao.PessoaDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Cidade;
import model.Configuracao;
import model.Dispositivo;
import model.Endereco;
import model.Pessoa;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Path("/usuario")
public class UsuarioService {

    @GET
    @Path("/logar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Pessoa logar(@QueryParam("email") String email,
            @QueryParam("senha") String senha) {
        LoginDAO loginDAO = new LoginDAO();
        Pessoa p = loginDAO.logar(email, util.Criptografia.criptografar(senha));
        return p;

    }

    @GET
    @Path("/empresaPorId")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    public Pessoa logar(@QueryParam("id") Long id) {
        PessoaDAO pDAO = new PessoaDAO();
        Pessoa p = pDAO.carregar(id);

        return p;

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/token")
    public Dispositivo atualizarDispositivo(String string) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(string).getAsJsonObject();
        Dispositivo dispositivo = gson.fromJson(o, Dispositivo.class);
        DispositivoDAO dDAO = new DispositivoDAO();

        List<Dispositivo> lista = dDAO.listarDispositivosToken(dispositivo.getTokenDispositivo());
        if (lista.size() > 0) {
            lista.stream().filter((d) -> (dispositivo.getIdEmpresa().equals(d.getIdEmpresa()))).forEach((d) -> {
                dDAO.deletar(d);
            });
        }
        Dispositivo d = dDAO.atualizarMobile(dispositivo);
        return d;
    }

    @GET
    @Path("/cidades")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Cidade> listaCidades() {
        CidadeDAO cDAO = new CidadeDAO();
        return cDAO.listar();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/editar")
    public Pessoa atualizarDados(String string) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(string).getAsJsonObject();
        System.out.println(o);
        Pessoa pessoa = gson.fromJson(o, Pessoa.class);
        PessoaDAO pDAO = new PessoaDAO();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Pessoa p = pDAO.atualizarMobile(pessoa);
        return p;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("/editarEndereco")
    public String atualizarEndereco(String string) {

        System.out.println(string);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(string).getAsJsonObject();
        Endereco endereco = gson.fromJson(o, Endereco.class);
        EnderecoDAO eDAO = new EnderecoDAO();
        JSONObject json;
        JSONArray array = new JSONArray();
        endereco.setStatus("Ativo");
        Endereco e = eDAO.atualizarMobile(endereco);
        if (e != null) {
            json = new JSONObject(e);
        } else {
            array.put("Erro");
            json = new JSONObject();
            json.put("resposta", array);

        }
        System.out.println(json);
        return json.toString();

    }

    @GET
    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ArrayList listarEmpresas(@QueryParam("quant") Integer quant, @QueryParam("total") Integer total, @QueryParam("categoria") String categoria) {
        PessoaDAO pDAO = new PessoaDAO();
        return (ArrayList) pDAO.listar(quant, total, categoria);
    }

    @GET
    @Path("empresasPorCidade")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ArrayList listarEmpresasPorCidade(@QueryParam("quant") Integer quant, @QueryParam("total") Integer total, @QueryParam("categoria") String categoria, @QueryParam("cidade") String cidade) {
        PessoaDAO pDAO = new PessoaDAO();
        return (ArrayList) pDAO.listar(quant, total, categoria, cidade);
    }

    @GET
    @Path("mural")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Configuracao> listarConfiguracao() {
        ConfiguracaoDAO cDAO = new ConfiguracaoDAO();
        return cDAO.listar();
    }
}
