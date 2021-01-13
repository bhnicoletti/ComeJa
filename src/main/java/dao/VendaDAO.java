/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.LoginBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import model.Dispositivo;
import model.Ingrediente;
import model.ItemVenda;
import model.Pessoa;
import model.Produto;
import model.Venda;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.Filtro;
import util.HibernateUtil;
import util.Util;

/**
 * <p>
 * VendaDAO class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class VendaDAO {

    private Session sessao;
    private Transaction trans;
    private List<Venda> list;

    public String cadastrar(Venda v) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar teste = new GregorianCalendar();
            SimpleDateFormat teste2 = new SimpleDateFormat("HH:mm:h",locale);;            
            v.setHora(teste2.format(teste.getTime()));

            for (Iterator<ItemVenda> i = v.getCarrinho().iterator(); i.hasNext();) {
                ArrayList<Ingrediente> ingredientesRetirados = new ArrayList();
                ItemVenda item = i.next();

                if (!item.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida") && item.getIngredientesProduto().size() != item.getProdutoItemVenda().getIngredientesProduto().size()) {
                    for (Iterator<Ingrediente> inp = item.getProdutoItemVenda().getIngredientesProduto().iterator(); inp.hasNext();) {
                        Ingrediente ip = inp.next();

                        Ingrediente n = ip;

                        for (Iterator<Ingrediente> ing = item.getIngredientesProduto().iterator(); ing.hasNext();) {
                            Ingrediente it = ing.next();
                            if (ip.getIdIngrediente().equals(it.getIdIngrediente())) {
                                n = null;
                            }
                        }
                        if (ingredientesRetirados.isEmpty() && n != null) {
                            ingredientesRetirados.add(n);
                        } else {
                            //problema aqui abaixo
                            for (int a = 0; a < ingredientesRetirados.size(); a++) {
                                Ingrediente in = ingredientesRetirados.get(a);
                                if (in.equals(n)) {
                                    n = null;
                                }
                            }
                            if (n != null) {
                                ingredientesRetirados.add(n);
                            }
                        }

                    }
                }
                item.setIngredientesRetirados(ingredientesRetirados);
                ingredientesRetirados = new ArrayList();
            }

            Venda venda = (Venda) sessao.merge(v);
            for (ItemVenda item : venda.getCarrinho()) {
                item.setVendaItemVenda(venda.getIdVenda());
                sessao.update(item);
            }
            trans.commit();
            DispositivoDAO dDAO = new DispositivoDAO();
            List<Dispositivo> dispositivos = dDAO.listarDispositivos(venda.getEmpresa().getIdPessoa());
            for (Dispositivo d : dispositivos) {
                enviarNotificacao(d.getTokenDispositivo(), "Pedido nº: " + venda.getIdVenda(), "Novo Pedido");
            }
            return "Pedido realizado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao fazer pedido: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }

    }

    public boolean cadastrarVendaMobile(Venda v) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();

        try {
            Locale locale = new Locale("pt", "BR");
            GregorianCalendar teste = new GregorianCalendar();
            SimpleDateFormat teste2 = new SimpleDateFormat("HH:mm:h",locale);;            
            v.setHora(teste2.format(teste.getTime()));
            
            for (Iterator<ItemVenda> i = v.getCarrinho().iterator(); i.hasNext();) {
                ArrayList<Ingrediente> ingredientesRetirados = new ArrayList();
                ItemVenda item = i.next();

                ProdutoDAO pDAO = new ProdutoDAO();
                Produto p = pDAO.carregar(item.getProdutoItemVenda().getIdProduto());

                if (!item.getProdutoItemVenda().getCategoriaProduto().getTituloCategoria().equals("Bebida") && item.getIngredientesProduto().size() != p.getIngredientesProduto().size()) {
                    for (Iterator<Ingrediente> inp = p.getIngredientesProduto().iterator(); inp.hasNext();) {
                        Ingrediente ip = inp.next();

                        Ingrediente n = ip;

                        for (Iterator<Ingrediente> ing = item.getIngredientesProduto().iterator(); ing.hasNext();) {
                            Ingrediente it = ing.next();
                            if (ip.getIdIngrediente().equals(it.getIdIngrediente())) {
                                n = null;
                            }
                        }
                        if (ingredientesRetirados.isEmpty() && n != null) {
                            ingredientesRetirados.add(n);
                        } else {
                            for (int a = 0; a < ingredientesRetirados.size(); a++) {
                                Ingrediente in = ingredientesRetirados.get(a);
                                if (in.equals(n)) {
                                    n = null;
                                }
                            }
                            if (n != null) {
                                ingredientesRetirados.add(n);
                            }
                        }

                    }
                }
                item.setIngredientesRetirados(ingredientesRetirados);
                ingredientesRetirados = new ArrayList();
            }
            Venda venda = (Venda) sessao.merge(v);
            for (ItemVenda item : venda.getCarrinho()) {
                item.setVendaItemVenda(venda.getIdVenda());
                sessao.update(item);
            }
            trans.commit();
            DispositivoDAO dDAO = new DispositivoDAO();
            List<Dispositivo> dispositivos = dDAO.listarDispositivos(venda.getEmpresa().getIdPessoa());
            for (Dispositivo d : dispositivos) {
                enviarNotificacao(d.getTokenDispositivo(), "Pedido nº: " + venda.getIdVenda(), "Novo Pedido");
            }

            return true;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            HibernateUtil.clearSession();
        }

    }

    public void atualizar(Venda v) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.update(v);
            trans.commit();
        } catch (HibernateException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());

        } finally {
            HibernateUtil.clearSession();
        }

    }

    public boolean atualizarMobile(Venda v) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            DispositivoDAO dDAO = new DispositivoDAO();
            List<Dispositivo> dispositivos = dDAO.listarDispositivos(v.getCliente().getIdPessoa());
            System.out.println(v.getCliente().getIdPessoa());
            if (!dispositivos.isEmpty()) {
                for (Dispositivo d : dispositivos) {
                    enviarNotificacaoUsuario(d.getTokenDispositivo(), "O seu pedido foi: " + v.getStatusVenda(), "ComeJá");
                }
            }
            sessao.merge(v);
            trans.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return false;

        } finally {
            HibernateUtil.clearSession();
        }

    }

    public ArrayList<Venda> listar(Long cliente) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Venda.class);
            criteria.add(Restrictions.ne("statusVenda", "Cancelada"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.addOrder(Order.desc("idVenda"));
            PessoaDAO cliDAO = new PessoaDAO();
            FacesContext c = FacesContext.getCurrentInstance();
            ELResolver r = c.getApplication().getELResolver();
            LoginBean loginBean = (LoginBean) r.getValue(c.getELContext(), null, "loginBean");
            criteria.add(Restrictions.eq("cliente", loginBean.getPessoaAtual()));
            criteria.setMaxResults(10);

            List results = criteria.list();
            return (ArrayList<Venda>) results;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return null;
    }

    public List listarMobile(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idVenda, formPagamento, to_char(dataVenda, 'DD/MM/YYYY') as dataVenda, vlrTotalVenda from venda where cliente_idpessoa=" + id + " order by idVenda desc LIMIT 10;";
            org.hibernate.Query query = sessao.createSQLQuery(sql);
            List results = query.list();
            return results;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return null;
    }

    public List<Venda> listarNotificarUsuario(Pessoa id) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Venda.class);
            criteria.add(Restrictions.ne("statusVenda", "Cancelada"));
            criteria.add(Restrictions.ne("statusVenda", "Ativo"));
            criteria.add(Restrictions.ne("statusVenda", "Finalizado"));
            criteria.add(Restrictions.ne("statusVenda", "Finalizada"));
            criteria.add(Restrictions.eq("cliente", id));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List results = criteria.list();
            System.out.println(results.size());
            return results;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return null;
    }

    public List listarMobileEmpresa(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idVenda, nomefantasiapessoa, to_char(dataVenda, 'DD/MM/YYYY') as dataVenda, vlrTotalVenda from venda, pessoa where empresa_idpessoa=" + id + " and cliente_idpessoa=idpessoa and (statusvenda = 'Ativo' or statusvenda = 'Aceito') order by idVenda desc LIMIT 10;";
            org.hibernate.Query query = sessao.createSQLQuery(sql);
            List results = query.list();
            return results;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return null;
    }

    public Venda carregar(Long i) {
        Venda venda = null;
        try {
            sessao = HibernateUtil.getSession();
            venda = (Venda) sessao.load(Venda.class, i);
            return venda;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return venda;
    }

    public String verificarNovasVendas() {
        try {
            sessao = HibernateUtil.getSession();

            String sql = "SELECT count(idvenda) FROM venda where statusvenda = 'Ativo' and empresa_idpessoa=" + Util.retornaEmpresaFixa().getIdPessoa();
            org.hibernate.Query query = sessao.createSQLQuery(sql);
            List result = query.list();
            return result.get(0).toString();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return null;

    }

    public List<Venda> filtradosEntrega(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroEntrega(filtro);

            criteria.setFirstResult(filtro.getPrimeiroRegistro());
            criteria.setMaxResults(filtro.getQuantidadeRegistros());

            if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
                criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
            } else if (filtro.getPropriedadeOrdenacao() != null) {
                criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
            }
            return criteria.list();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public int quantidadeFiltradosEntrega(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroEntrega(filtro);

            criteria.setProjection(Projections.rowCount());

            return ((Number) criteria.uniqueResult()).intValue();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return 0;
        }
    }

    private Criteria criarCriteriaParaFiltroEntrega(Filtro filtro) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Venda.class);
            criteria.add(Restrictions.eq("empresa", Util.retornaEmpresaFixa()));
            criteria.add(Restrictions.eq("statusVenda", "Ativo"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (filtro.getData() != null) {
                criteria.add(Restrictions.eq("dataVenda", filtro.getData()));
            }
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public List<Venda> filtrados(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltro(filtro);

            criteria.setFirstResult(filtro.getPrimeiroRegistro());
            criteria.setMaxResults(filtro.getQuantidadeRegistros());

            if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
                criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
            } else if (filtro.getPropriedadeOrdenacao() != null) {
                criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
            }
            return criteria.list();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public int quantidadeFiltrados(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltro(filtro);

            criteria.setProjection(Projections.rowCount());

            return ((Number) criteria.uniqueResult()).intValue();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return 0;
        }
    }

    private Criteria criarCriteriaParaFiltro(Filtro filtro) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Venda.class);
            criteria.add(Restrictions.eq("empresa", Util.retornaEmpresaFixa()));
            criteria.add(Restrictions.ne("statusVenda", "Cancelada"));
            criteria.add(Restrictions.ne("statusVenda", "Finalizada"));
            criteria.add(Restrictions.ne("statusVenda", "Finalizado"));
            criteria.add(Restrictions.ne("statusVenda", "Recusado"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (filtro.getData() != null) {
                criteria.add(Restrictions.eq("dataVenda", filtro.getData()));
            }
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public void enviarNotificacao(String token, String menssagem, String titulo) {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Authorization", "Key=AIzaSyDf--JzU16uBDY5Bu2t_Gq4JTnryBY3xZ8");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
            System.out.println("Connected!");

            String outputBytes = "{\"notification\":{\"title\": \"" + titulo + "\", \"text\": \"" + menssagem + "\", \"sound\": \"default\"}, \"to\": \"" + token + "\"}";
            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes.getBytes("UTF-8"));
            os.close();

            // Reading response
            InputStream input = httpcon.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }

            System.out.println("Http POST request sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarNotificacaoUsuario(String token, String menssagem, String titulo) {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Authorization", "Key=AIzaSyCCkWbktfRxFIVoFidWs3BrTNkzdQcnra0");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
            System.out.println("Connected!");

            String outputBytes = "{\"notification\":"
                    + "{\"title\": \"" + titulo + "\","
                    + " \"text\": \"" + menssagem + "\","
                    + " \"icon\": \"logo\","
                    + " \"sound\": \"default\"},"
                    + " \"priority\": \"high\","
                    + " \"time_to_live\": 14400,"
                    + " \"to\": \"" + token + "\"}";
            // System.out.println(outputBytes);
            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes.getBytes("UTF-8"));
            os.close();

            // Reading response
            InputStream input = httpcon.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }

            System.out.println("Http POST request sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
