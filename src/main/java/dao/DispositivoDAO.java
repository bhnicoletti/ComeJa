/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Dispositivo;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author Nicoletti
 */
public class DispositivoDAO {

    private Session sessao;
    private Transaction trans;

    public String cadastrar(Dispositivo d) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            List<Dispositivo> lista = listarDispositivosToken(d.getTokenDispositivo());
            if (lista.size() > 0) {
                for (Dispositivo di : lista) {
                    deletar(di);
                }
            }
            sessao.save(d);
            trans.commit();
            return "Cadastrado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao cadastrar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    public Dispositivo atualizarMobile(Dispositivo en) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            System.out.println("Gravando");

            Dispositivo e = (Dispositivo) sessao.merge(en);
            trans.commit();
            return e;
        } catch (HibernateException e) {
            return null;
        } finally {
            HibernateUtil.clearSession();
        }
    }

    public Dispositivo carregar(Long i) {
        Dispositivo e = null;
        try {
            sessao = HibernateUtil.getSession();
            e = (Dispositivo) sessao.load(Dispositivo.class, i);
            return e;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return e;
    }

    public String deletar(Dispositivo d) {
        trans = sessao.beginTransaction();
        try {

            sessao.delete(d);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao deletar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }

    }

    public List<Dispositivo> listarDispositivos(Long id) {
        try {
            sessao = HibernateUtil.getSession();
            Criteria cri = sessao.createCriteria(Dispositivo.class);
            cri.add(Restrictions.eq("idEmpresa", id));
            return cri.list();
        } catch (Exception ex) {
            return null;
        }
        finally {
            HibernateUtil.clearSession();
        }
    }

    public List<Dispositivo> listarDispositivosToken(String token) {
        try {
            sessao = HibernateUtil.getSession();

            Criteria cri = sessao.createCriteria(Dispositivo.class);
            cri.add(Restrictions.eq("tokenDispositivo", token));
            return cri.list();
        } catch (Exception ex) {
            return null;
        } finally {
            HibernateUtil.clearSession();
        }
    }
}
