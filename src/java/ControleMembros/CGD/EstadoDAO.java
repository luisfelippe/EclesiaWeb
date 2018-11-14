/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Estado;
import ControleMembros.CLN.CDP.Pais;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LuísFelippe
 */
public class EstadoDAO extends DAOBase{
    public List<Estado> getLista(Pais p) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Estado.class);
            
            if(p != null)
                criteria.add(Restrictions.eq("pais", p));
            
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
