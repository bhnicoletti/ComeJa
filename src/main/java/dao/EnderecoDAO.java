/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Endereco;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;


public class EnderecoDAO {

    private Session sessao;
    private Transaction trans;

    
    public String cadastrar(Endereco en) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            
            sessao.save(en);
            trans.commit();
            return "Cadastrado com sucesso";
        } catch (HibernateException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return "Erro ao cadastrar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }

    
    public String atualizar(Endereco en) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            sessao.merge(en);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }
    }
    
    
    public Endereco atualizarMobile(Endereco en) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {
            

            Endereco e = (Endereco) sessao.merge(en);
            trans.commit();
            System.out.println(e.getIdEndereco());
            return e;
        } catch (HibernateException e) {
            return null;
        } finally {
            HibernateUtil.clearSession();
        }
    }
    
    


   
    public Endereco carregar(Long i) {
        Endereco e = null;
        try {
            sessao = HibernateUtil.getSession();
            e = (Endereco) sessao.load(Endereco.class, i);
            return e;
        } catch (Exception ex) {
            System.out.println("Erro ao carregar: " + ex.getMessage());
        } finally {
            HibernateUtil.clearSession();
        }
        return e;
    }

    
    public String deletar(Endereco en) {
        sessao = HibernateUtil.getSession();
        trans = sessao.beginTransaction();
        try {

            en.setStatus("Deletado");
            sessao.merge(en);
            trans.commit();
            return "Salvo com sucesso";
        } catch (HibernateException e) {
            return "Erro ao atualizar: " + e.getMessage();
        } finally {
            HibernateUtil.clearSession();
        }

    }
    
    
    public List<Endereco> listarEnderecosPessoa(Long id){
        try{
            sessao = HibernateUtil.getSession();
            
            Criteria cri = sessao.createCriteria(Endereco.class);
            cri.add(Restrictions.eq("pessoa", id));
            cri.add(Restrictions.ne("status", "Deletado"));
            return cri.list();
        }catch(Exception ex){
            return null;
        }
    }

    
}
