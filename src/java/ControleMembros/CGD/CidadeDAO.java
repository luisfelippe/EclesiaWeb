/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Cidade;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LuísFelippe
 */
public class CidadeDAO {
    public List<Cidade> getLista(long est) throws Exception {
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Cidade.class);
            
            if(est > 0)
                criteria.add(Restrictions.eq("estado.id", est));
            
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
