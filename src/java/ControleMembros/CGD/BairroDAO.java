/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Bairro;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LuísFelippe
 */
public class BairroDAO {
    public List<Bairro> getLista(long cid) throws Exception {
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Bairro.class);
            
            if(cid > 0)
                criteria.add(Restrictions.eq("cidade.id", cid));
            
            criteria.addOrder(Order.asc("nome"));
            
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
