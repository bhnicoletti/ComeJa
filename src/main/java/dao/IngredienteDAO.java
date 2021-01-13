/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mchange.v2.lang.StringUtils;
import java.util.List;
import model.Ingrediente;
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
 * <p>IngredienteDAO class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class IngredienteDAO {

    private Session sessao;
    private Transaction trans;
    private List<Ingrediente> list;

    /**
     * <p>cadastrar.</p>
     *
     * @param i a {@link model.Ingrediente} object.
     * @return a {@link java.lang.String} object.
     */
    public String cadastrar(Ingrediente i) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.save(i);
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
     * <p>atualizar.</p>
     *
     * @param i a {@link model.Ingrediente} object.
     * @return a {@link java.lang.String} object.
     */
    public String atualizar(Ingrediente i) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.merge(i);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    /**
     * <p>filtrados.</p>
     *
     * @param filtro a {@link util.Filtro} object.
     * @return a {@link java.util.List} object.
     */
    public List<Ingrediente> filtrados(Filtro filtro) {
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
            Criteria criteria = sessao.createCriteria(Ingrediente.class);

            criteria.add(Restrictions.eq("empresa", util.Util.retornaEmpresa().getIdPessoa()));
            if (StringUtils.nonEmptyString(filtro.getDescricao())) {
                criteria.add(Restrictions.ilike("nomeIngrediente", filtro.getDescricao(), MatchMode.ANYWHERE));
            }

            return criteria;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }
    }

    /**
     * <p>carregar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link model.Ingrediente} object.
     */
    public Ingrediente carregar(Long id) {
        Ingrediente i = null;
        try {
            sessao = HibernateUtil.getSession();
            i = (Ingrediente) sessao.load(Ingrediente.class, id);
            return i;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return i;
    }

    /**
     * <p>deletar.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link java.lang.String} object.
     */
    public String deletar(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            String sql = "delete from Ingrediente where idIngrediente = " + id;
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
     * <p>listar.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Ingrediente> listar() {
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Ingrediente.class);
            cri.add(Restrictions.eq("empresa", util.Util.retornaEmpresa().getIdPessoa()));
            this.list = cri.list();
        } catch (HibernateException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }

        return list;
    }
    
    /**
     * <p>listarAdicional.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Ingrediente> listarAdicional() {
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Ingrediente.class);
            cri.add(Restrictions.eq("empresa", util.Util.retornaEmpresa().getIdPessoa()));
            cri.add(Restrictions.eq("adicional", true));
            this.list = cri.list();
        } catch (HibernateException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }

        return list;
    }
    
    
    public List<Ingrediente> listarAdicional(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Ingrediente.class);
            cri.add(Restrictions.eq("empresa", id));
            cri.add(Restrictions.eq("adicional", true));
            this.list = cri.list();
        } catch (HibernateException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }

        return list;
    }

}
