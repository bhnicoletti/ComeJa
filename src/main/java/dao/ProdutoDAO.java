/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mchange.v2.lang.StringUtils;
import java.util.List;
import model.Produto;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import util.Filtro;
import util.HibernateUtil;
import util.Util;

public class ProdutoDAO {

    private Session sessao;
    private Transaction trans;

    public String cadastrar(Produto p) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            if(p.getImagemProduto().length()==0){
                p.setImagemProduto("semimagem.jpg");
            }
            sessao.merge(p);
            trans.commit();
            return "Cadastrado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao cadastrar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    public String atualizar(Produto p) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.merge(p);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    public List<Produto> listar(String s) {
        sessao = HibernateUtil.getSession();
        Criteria cri = sessao.createCriteria(Produto.class);
        cri.add(Restrictions.eq("empresaProduto", Util.retornaEmpresaFixa()));
        cri.add(Restrictions.like("nomeProduto", s, MatchMode.START));
        cri.addOrder(Order.asc("nomeProduto"));
        return cri.list();

    }

    public List<Produto> filtrados(Filtro filtro) {
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
            Criteria criteria = sessao.createCriteria(Produto.class);

            ProjectionList prjection = Projections.projectionList();
            prjection.add(Projections.property("idProduto"), "idProduto");
            prjection.add(Projections.property("nomeProduto"),"nomeProduto");
            prjection.add(Projections.property("imagemProduto"), "imagemProduto");
            criteria.setProjection(prjection);
            

            criteria.add(Restrictions.eq("empresaProduto", util.Util.retornaEmpresa()));
            criteria.add(Restrictions.eq("statusProduto", "Ativo"));
            if (filtro.getCategoriaProduto() != null) {
                criteria.add(Restrictions.eq("categoriaProduto", filtro.getCategoriaProduto()));
            }

            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.setResultTransformer(Transformers.aliasToBean(Produto.class));

            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public Produto carregar(Long id) {
        Produto p = null;
        try {
            sessao = HibernateUtil.getSession();
            p = (Produto) sessao.load(Produto.class, id);
          
            return p;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return p;
    }

    public String deletar(Produto p) {
        try {
            sessao.close();
            sessao = HibernateUtil.getSession();
            sessao.delete(p);
            return "Deletado com sucesso";
        } catch (Exception ex) {
            return "Erro ao deletar: " + ex.getMessage();

        } finally {
            HibernateUtil.clearSession();
        }

    }

    public List listar(Integer id, Integer quant, Integer total) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idProduto, nomeProduto, valorProduto, imagemProduto from produto where empresaproduto_idpessoa=" + id + " and statusProduto='Ativo' order by nomeProduto LIMIT " + quant + " OFFSET " + total + ";";
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

    public List listar(Integer id, Integer quant, Integer total, String categoria) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idProduto, nomeProduto, valorProduto, imagemProduto from produto, categoria where empresaproduto_idpessoa=" + id + "and statusProduto='Ativo' and categoriaproduto_idcategoria=idCategoria and tituloCategoria like '" + categoria + "' order by nomeProduto LIMIT " + quant + " OFFSET " + total + ";";
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

    public List listarProdutosEmpresa(Integer id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idProduto, nomeProduto, statusProduto from produto where empresaproduto_idpessoa=" + id + "";
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

    public List<Produto> listaTest(Integer id, Integer quant, Integer total) {
        sessao = HibernateUtil.getSession();
        Criteria cri = sessao.createCriteria(Produto.class);
        PessoaDAO pDAO = new PessoaDAO();
        cri.add(Restrictions.eq("empresaProduto", pDAO.carregar(Long.parseLong(id.toString()))));
        cri.setMaxResults(quant);
        cri.setFirstResult(total);
        cri.addOrder(Order.asc("nomeProduto"));
        return cri.list();

    }

    public List<Produto> filtradosEmpresa(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroEmpresa(filtro);
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

    public int quantidadeFiltradosEmpresa(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroEmpresa(filtro);

            criteria.setProjection(Projections.rowCount());

            return ((Number) criteria.uniqueResult()).intValue();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return 0;
        }
    }

    private Criteria criarCriteriaParaFiltroEmpresa(Filtro filtro) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Produto.class);

            criteria.add(Restrictions.eq("empresaProduto", util.Util.retornaEmpresa()));

            if (StringUtils.nonEmptyString(filtro.getDescricao())) {
                criteria.add(Restrictions.ilike("nomeProduto", filtro.getDescricao(), MatchMode.ANYWHERE));
            }
            if (filtro.getCategoriaProduto() != null) {
                criteria.add(Restrictions.eq("categoriaProduto", filtro.getCategoriaProduto()));
            }

            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    public List listaProdutosMaisVendidosPorEmpresa(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "SELECT produto.nomeproduto as produto, sum(itemvenda.quantitemvenda) as quant FROM produto Inner Join itemvenda On produto.idproduto = itemvenda.produtoitemvenda_idproduto\n"
                    + "WHERE produto.empresaproduto_idpessoa = " + id + " GROUP BY produto.nomeproduto ORDER BY sum(itemvenda.quantitemvenda) DESC Limit 10";
            org.hibernate.Query query = sessao.createSQLQuery(sql);
            List results = query.list();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
