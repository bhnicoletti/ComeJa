/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mchange.v2.lang.StringUtils;
import java.util.List;
import model.Categoria;
import model.Configuracao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.Filtro;
import util.HibernateUtil;

/**
 *
 * @author Nicoletti
 */
public class ConfiguracaoDAO {
    private Session sessao;
    private Transaction trans;
    private List<Configuracao> list;

    
    

    
    public String salvar(Configuracao s) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.merge(s);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }
    
    public Configuracao carregar(Long i) {
        Configuracao c = null;
        try {
            sessao = HibernateUtil.getSession();
            c = (Configuracao) sessao.load(Configuracao.class, i);
            return c;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return c;
    }
    
     public String deletar(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "delete from Sistema where id = " + id;
            Query query = sessao.createQuery(sql);
            query.executeUpdate();
            return "Deletado com sucesso";
        } catch (Exception ex) {            
            return "Erro ao deletar: " + ex.getMessage();

        } finally {
            HibernateUtil.clearSession();
        }
    }
     
     
     public List<Configuracao> filtrados(Filtro filtro) {
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
     * <p>quantidadeFiltrados.</p>
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
            Criteria criteria = sessao.createCriteria(Configuracao.class);                   
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }
    
    public List<Configuracao> listar() {        
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Configuracao.class);
            cri.add(Restrictions.eq("status", true));
            this.list = cri.list();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            System.out.println("Erro: " + ex.getMessage());
        }
        finally {
            HibernateUtil.clearSession();
        }
        return list;
    }
    
    public List<Configuracao> listarPaginaInicial() {        
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Configuracao.class);
            cri.add(Restrictions.eq("paginainicial", true));
            this.list = cri.list();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            System.out.println("Erro: " + ex.getMessage());
        }
        finally {
            HibernateUtil.clearSession();
        }
        return list;
    }
}
