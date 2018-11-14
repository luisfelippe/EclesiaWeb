/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cgd;

import ControleMembros.CGD.DAOBase;
import ControleMembros.CGD.HibernateUtil;
import controlefinanceiro.cln.cdp.Entrada;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Implementa a camada de acesso aos dados de uma entrada
 * @author LuísFelippe
 */
public class EntradaDAO extends DAOBase{

    public List<Entrada> getListaDizimista(Date dataIni, Date dataFim) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Entrada.class);
            //ProjectionList pl = Projections.projectionList();
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.ge("data", dataFim ) );
            }
            
            criteria.add( Restrictions.isNotNull("membro") );
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
    
    public long getQtdContribuintes() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	DISTINCT e.membro " +
                        "FROM " +
                        "	entrada e " +
                        "WHERE " +
                        "	e.data BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "GROUP BY " +
                        "	e.membro " +
                        "HAVING " +
                        "	e.membro IS NOT NULL ";
            
            long qtd = session.createSQLQuery(sql).list().size();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public double getValorMedioPorContribuinte() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	ROUND(AVG(soma),2) " +
                        "FROM " +
                        "	( " +
                        "		SELECT " +
                        "			SUM(valor) AS soma" +
                        "		FROM " +
                        "			entrada e " +
                        "		WHERE " +
                        "			e.data BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "		GROUP BY " +
                        "			e.membro " +
                        "		HAVING " +
                        "			e.membro IS NOT NULL" +
                        "	) AS v";
            
            double qtd = ((Number)session.createSQLQuery(sql).uniqueResult()).doubleValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }

    public List<Entrada> getLista(Date dataIni, Date dataFim) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Entrada.class);
            //ProjectionList pl = Projections.projectionList();
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.ge("data", dataFim ) );
            }
            
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
