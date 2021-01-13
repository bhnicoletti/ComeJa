/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Pessoa;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import util.Filtro;
import util.HibernateUtil;

/**
 * <p>
 * PessoaDAO class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class PessoaDAO {
    
    private Session sessao;
    private Transaction trans;

    /**
     * <p>
     * cadastrar.</p>
     *
     * @param p a {@link model.Pessoa} object.
     * @return a {@link java.lang.String} object.
     */
    public String cadastrar(Pessoa p) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            
            sessao.save(p);
            trans.commit();
            return "Cadastrado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao cadastrar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    /**
     * <p>
     * atualizar.</p>
     *
     * @param p a {@link model.Pessoa} object.
     * @return a {@link java.lang.String} object.
     */
    public String atualizar(Pessoa p) {
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
    
    public Boolean atualizarBoolean(Pessoa p) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            
            sessao.merge(p);
            trans.commit();
            return true;
        } catch (HibernateException e) {
            return false;
        } finally {
            HibernateUtil.clearSession();
        }
    }

    /**
     * <p>
     * filtrados.</p>
     *
     * @param filtro a {@link util.Filtro} object.
     * @return a {@link java.util.List} object.
     */
    public List<Pessoa> filtrados(Filtro filtro) {
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

    /**
     * <p>
     * quantidadeFiltrados.</p>
     *
     * @param filtro a {@link util.Filtro} object.
     * @return a int.
     */
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
            Criteria criteria = sessao.createCriteria(Pessoa.class);
            ProjectionList prjection = Projections.projectionList();
            prjection.add(Projections.property("idPessoa"),"idPessoa");
            prjection.add(Projections.property("nomeFantasiaPessoa"), "nomeFantasiaPessoa");            
            prjection.add(Projections.property("imagem"), "imagem");
            criteria.setProjection(prjection);
            if (!"".equals(filtro.getTipo())) {
                criteria.add(Restrictions.eq("tipoPessoa", filtro.getTipo()));
            }
            if (!"".equals(filtro.getCategoria())) {
                criteria.add(Restrictions.eq("categoriaPessoa", filtro.getCategoria()));
            }
             if (filtro.getCidadePessoa() != null) {
                criteria.add(Restrictions.eq("cidade", filtro.getCidadePessoa()));
            }
            criteria.add(Restrictions.eq("statusPessoa", "Ativo"));
            
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.setResultTransformer(Transformers.aliasToBean(Pessoa.class));
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * carregar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link model.Pessoa} object.
     */
    public Pessoa carregar(Long id) {
        Pessoa p = null;
        try {
            sessao = HibernateUtil.getSession();
            p = (Pessoa) sessao.load(Pessoa.class, id);
            EnderecoDAO eDAO = new EnderecoDAO();
            p.setEnderecos(eDAO.listarEnderecosPessoa(p.getIdPessoa()));
            return p;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return p;
    }

    /**
     * <p>
     * deletar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link java.lang.String} object.
     */
    public String deletar(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "delete from Pessoa where idPessoa = " + id;
            Query query = sessao.createQuery(sql);
            query.executeUpdate();
            return "Deletado com sucesso";
        } catch (Exception ex) {
            return "Erro ao deletar: " + ex.getMessage();
            
        } finally {
            HibernateUtil.clearSession();
        }
    }

    /**
     * <p>
     * listar.</p>
     *
     * @param quant a {@link java.lang.Integer} object.
     * @param total a {@link java.lang.Integer} object.
     * @param categoria
     * @return a {@link java.util.List} object.
     */
    public List listar(Integer quant, Integer total, String categoria) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "select idPessoa, nomeFantasiaPessoa, imagem from pessoa where tipoPessoa='Empresa' and statusPessoa='Ativo' and categoriaPessoa='" + categoria + "' order by nomeFantasiaPessoa LIMIT " + quant + " OFFSET " + total + ";";
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
    
    public List listar(Integer quant, Integer total, String categoria, String cidade) {
        try {
            sessao = HibernateUtil.getSession();
           String sql = "select idPessoa, nomeFantasiaPessoa, imagem from pessoa, cidade where tipoPessoa='Empresa' and cidade_id=id and nome like '" + cidade + "' and statusPessoa='Ativo' and categoriaPessoa='" + categoria + "' order by nomeFantasiaPessoa LIMIT " + quant + " OFFSET " + total + ";";
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
    
    public Pessoa atualizarMobile(Pessoa pessoa) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            
            Pessoa e = (Pessoa) sessao.merge(pessoa);
            trans.commit();
            return e;
        } catch (HibernateException e) {
            return null;
        } finally {
            HibernateUtil.clearSession();
        }
    }
    
    public List<Pessoa> filtradosADM(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroADM(filtro);
            
            criteria.setFirstResult(filtro.getPrimeiroRegistro());
            criteria.setMaxResults(filtro.getQuantidadeRegistros());
            
            if (!"".equals(filtro.getTipo())) {
                criteria.add(Restrictions.eq("tipoPessoa", filtro.getTipo()));
            }
            
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

    /**
     * <p>
     * quantidadeFiltrados.</p>
     *
     * @param filtro a {@link util.Filtro} object.
     * @return a int.
     */
    public int quantidadeFiltradosADM(Filtro filtro) {
        try {
            Criteria criteria = criarCriteriaParaFiltroADM(filtro);
            
            criteria.setProjection(Projections.rowCount());
            return ((Number) criteria.uniqueResult()).intValue();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return 0;
        }
    }
    
    private Criteria criarCriteriaParaFiltroADM(Filtro filtro) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Pessoa.class);
            
            if (!"".equals(filtro.getTipo())) {
                criteria.add(Restrictions.eq("tipoPessoa", filtro.getTipo()));
            }
            
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }
    
    public Pessoa pesquisarEmail(String email) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria criteria = sessao.createCriteria(Pessoa.class);            
            criteria.add(Restrictions.eq("emailPessoa", email)); 
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return (Pessoa) criteria.list().get(0);
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }
    
    public List listaVendaMes(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "SELECT EXTRACT(month FROM venda.datavenda) as mes, count(*) as valor FROM venda where venda.empresa_idpessoa="+id+"  Group by mes order by mes Limit 10";
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
