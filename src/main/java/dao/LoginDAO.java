/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Pessoa;
import model.Produto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 * <p>LoginDAO class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class LoginDAO {

    private Session sessao;

    /**
     * <p>logar.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @param senha a {@link java.lang.String} object.
     * @return a {@link model.Pessoa} object.
     */
    public Pessoa logar(String email, String senha) {

        try {
            sessao = HibernateUtil.getSession();

            Criteria cri = sessao.createCriteria(Pessoa.class)
                    .add(Restrictions.eq("emailPessoa", email))
                    .add(Restrictions.eq("senhaPessoa", senha));

            if (!cri.list().isEmpty()) {
                Pessoa p = (Pessoa) cri.list().get(0);
                EnderecoDAO eDAO = new EnderecoDAO();
                p.setEnderecos(eDAO.listarEnderecosPessoa(p.getIdPessoa()));
                return p;
            }
        } finally {
            HibernateUtil.clearSession();
        }
        return null;

    }

    /**
     * <p>logarMobile.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @param senha a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List logarMobile(String email, String senha) {

        try {
            sessao = HibernateUtil.getSession();

            sessao = HibernateUtil.getSession();
            String sql = "select idPessoa from pessoa where emailPessoa='" + email + "' and senhaPessoa='" + senha + "'";
            org.hibernate.Query query = sessao.createSQLQuery(sql);
           
            List lista = new ArrayList();
            lista.add(sessao.load(Pessoa.class, Long.parseLong(query.list().get(0).toString())));
            return lista;

        } finally {
            HibernateUtil.clearSession();
        }

    }
}
