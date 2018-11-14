/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Ministerio;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 *
 * @author luisfelippe
 */
public class MinisterioDAO extends DAOBase{
    public List getLista() throws Exception {
        // conexao com o banco de dados;
        Session session = null;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Ministerio.class);
            
            return criteria.list();            

        } catch (Exception e) {            
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        } 
    }
    
    public long getQtdMinisterios() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Ministerio.class);
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
}
