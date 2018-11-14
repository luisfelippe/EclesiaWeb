/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.GrauParentesco;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author LuísFelippe
 */
public class GrauParentescoDAO extends DAOBase {
    public List<GrauParentesco> getLista() throws Exception{
       
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(GrauParentesco.class);
            criteria.addOrder(Order.asc("descricao"));

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
