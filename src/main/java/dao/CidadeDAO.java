/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mchange.v2.lang.StringUtils;
import java.util.List;
import model.Categoria;
import model.Cidade;
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
 * <p>CategoriaDAO class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class CidadeDAO {

    private Session sessao;
    private Transaction trans;
    private List<Cidade> list;

 
    public String cadastrar(Cidade c) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.save(c);
            trans.commit();
            return "Cadastrado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao cadastrar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

  
    public String atualizar(Cidade c) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.merge(c);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

   
    public List<Cidade> filtrados(Filtro filtro) {
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
            Criteria criteria = sessao.createCriteria(Cidade.class);
            

            if (StringUtils.nonEmptyString(filtro.getDescricao())) {
                criteria.add(Restrictions.ilike("nome", filtro.getDescricao(), MatchMode.ANYWHERE));
            }
            
            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

 
    public Cidade carregar(Long i) {
        Cidade c = null;
        try {
            sessao = HibernateUtil.getSession();
            c = (Cidade) sessao.load(Cidade.class, i);
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
            String sql = "delete from Cidade where id = " + id;
            Query query = sessao.createQuery(sql);
            query.executeUpdate();
            return "Deletado com sucesso";
        } catch (Exception ex) {            
            return "Erro ao deletar: " + ex.getMessage();

        } finally {
            HibernateUtil.clearSession();
        }

    }

    public List<Cidade> listar() {        
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Cidade.class);
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
