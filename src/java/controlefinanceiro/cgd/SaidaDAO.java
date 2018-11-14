/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cgd;

import ControleMembros.CGD.DAOBase;
import ControleMembros.CGD.HibernateUtil;
import controlefinanceiro.cln.cdp.Saida;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa a camada de acesso aos dados de uma saida
 * @author LuísFelippe
 */
public class SaidaDAO extends DAOBase{
    public List<Saida> getLista(Date dataIni, Date dataFim) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Saida.class);
            //ProjectionList pl = Projections.projectionList();
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.ge("data", dataFim ) );
            }
            
            //criteria.add( Restrictions.isNotNull("membro") );
            /*pl.add(Projections.groupProperty("membro"));
            criteria.setProjection(pl);*/
            
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
