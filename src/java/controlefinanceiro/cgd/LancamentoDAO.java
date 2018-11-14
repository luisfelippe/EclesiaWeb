/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cgd;

import ControleMembros.CGD.DAOBase;
import ControleMembros.CGD.HibernateUtil;
import controlefinanceiro.cln.cdp.Lancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisfelippe
 */
public class LancamentoDAO extends DAOBase{

    public List<Lancamento> getListaDizimista(Date dataIni, Date dataFim) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Lancamento.class);
            //ProjectionList pl = Projections.projectionList();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
                //criteria.add( Restrictions.sqlRestriction("{alias}.data_lancamento >= '" + sdf.format(dataIni) + "'"));
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.le("data", dataFim ) );
                //criteria.add( Restrictions.sqlRestriction("{alias}.data_lancamento <= '" + sdf.format(dataFim) + "'"));
            }
            
            criteria.add( Restrictions.isNotNull("membro") );
            criteria.add( Restrictions.eq("tipo", TipoLancamento.ENTRADA) );
//            criteria.add( Restrictions.sqlRestriction("{alias}.tipo = 0 /*entrada*/"));
            
            criteria.addOrder(Order.asc("data"));
            
            return criteria.list();

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public List<Lancamento> getListaLancamento(Date dataIni, Date dataFim) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Lancamento.class);
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.le("data", dataFim ) );
            }            
            
            criteria.addOrder(Order.asc("data"));
            
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
                        "	e.id_membro, e.tipo " +
                        "FROM " +
                        "	lancamento e " +
                        "WHERE " +
                        "	e.data_lancamento BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "GROUP BY " +
                        "	e.id_membro " +
                        "HAVING " +
                        "	e.id_membro IS NOT NULL " +
                        "       AND e.tipo = 0 /*ENTRADA*/";
            
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
                        "			SUM(valor) AS soma, e.id_membro, e.tipo" +
                        "		FROM " +
                        "			lancamento e " +
                        "		WHERE " +
                        "			e.data_lancamento BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "		GROUP BY " +
                        "			e.id_membro " +
                        "		HAVING " +
                        "			e.id_membro IS NOT NULL" +
                        "                       AND e.tipo = 0 /*ENTRADA*/" +
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
    
    public double getValorSaidaMesAnterior() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	SUM(soma) " +
                        "FROM " +
                        "	( " +
                        "		SELECT " +
                        "			SUM(valor) AS soma, e.tipo" +
                        "		FROM " +
                        "			lancamento e " +
                        "		WHERE " +
                        "			e.data_lancamento BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "		GROUP BY " +
                        "			e.tipo " +
                        "		HAVING " +
                        "			e.tipo = 1 /*SAIDA*/" +
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
    
    public double getValorSaidaMes() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	SUM(soma) " +
                        "FROM " +
                        "	( " +
                        "		SELECT " +
                        "			SUM(valor) AS soma, e.tipo" +
                        "		FROM " +
                        "			lancamento e " +
                        "		WHERE " +
                        "			YEAR(e.data_lancamento) = YEAR(NOW()) AND MONTH(e.data_lancamento) = MONTH(NOW()) " +
                        "		GROUP BY " +
                        "			e.tipo " +
                        "		HAVING " +
                        "			e.tipo = 1 /*SAIDA*/" +
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
    
    public double getValorEntradaMesAnterior() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	SUM(soma) " +
                        "FROM " +
                        "	( " +
                        "		SELECT " +
                        "			SUM(valor) AS soma, e.tipo" +
                        "		FROM " +
                        "			lancamento e " +
                        "		WHERE " +
                        "			e.data_lancamento BETWEEN (DATE_ADD(ADDDATE(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH)), 1), INTERVAL -1 MONTH)) AND LAST_DAY(LAST_DAY(SUBDATE(CURDATE(), INTERVAL 1 MONTH))) " +
                        "		GROUP BY " +
                        "			e.tipo " +
                        "		HAVING " +
                        "			e.tipo = 0 /*ENTRADA*/" +
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
    
    public double getValorEntradaMes() throws Exception
    {
        Session session = null;

        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();

            String sql = "SELECT " +
                        "	SUM(soma) " +
                        "FROM " +
                        "	( " +
                        "		SELECT " +
                        "			SUM(valor) AS soma, e.tipo" +
                        "		FROM " +
                        "			lancamento e " +
                        "		WHERE " +
                        "			YEAR(e.data_lancamento) = YEAR(NOW()) AND MONTH(e.`data_lancamento`) = MONTH(NOW()) " +
                        "		GROUP BY " +
                        "			e.tipo " +
                        "		HAVING " +
                        "			e.tipo = 0 /*ENTRADA*/" +
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

    public List<Lancamento> getLista(Date dataIni, Date dataFim, TipoLancamento t) throws Exception {
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Lancamento.class);
            
            if( dataIni != null ){
                criteria.add( Restrictions.ge("data", dataIni ) );
            }
            
            if( dataFim != null ){
                criteria.add( Restrictions.ge("data", dataFim ) );
            }            

            criteria.add( Restrictions.eq("tipo", t) );
            
            criteria.addOrder(Order.asc("data"));
            
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
