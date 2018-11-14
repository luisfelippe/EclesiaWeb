/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Cargo;
import ControleMembros.CLN.CDP.Ministerio;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisfelippe
 */
public class CargoDAO extends DAOBase {
    public List getLista() throws Exception {
        // conexao com o banco de dados;
        Session session = null;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Cargo.class);
            
            return criteria.list();            

        } catch (Exception e) {            
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        } 
    }
    
    public List getLista(Ministerio m) throws Exception {
        // conexao com o banco de dados;
        Session session = null;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Cargo.class);
            criteria.add(Restrictions.eq("ministerio", m));
            
            return criteria.list();            

        } catch (Exception e) {            
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        } 
    }
}
