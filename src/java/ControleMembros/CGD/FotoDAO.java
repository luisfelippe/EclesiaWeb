/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Foto;
import ControleMembros.CLN.CDP.Pessoa;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisfelippe
 */
public class FotoDAO extends DAOBase {
    public Foto getFotoPadrao(Pessoa pessoa) throws Exception{
    // conexao com o banco de dados;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Foto.class);
            criteria.createAlias("Pessoa", "p");
            
            criteria.add( Restrictions.eq("p.id", pessoa.getId() ) );
            criteria.addOrder(Order.desc("nrOrdem"));
            
            return (Foto) criteria.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null && session.isOpen()) 
                session.close();
        }
    }
    
    public List<Foto> getLista(Pessoa pessoa) throws Exception{
    // conexao com o banco de dados;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Foto.class);
            criteria.createAlias("pessoa", "p");
            
            criteria.add( Restrictions.eq("p.id", pessoa.getId() ) );
            criteria.addOrder(Order.desc("nrOrdem"));
            
            return criteria.list();

        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null && session.isOpen()) 
                session.close();
        }
    }
}
