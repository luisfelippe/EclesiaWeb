package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Visitante;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class VisitanteDAO extends DAOBase {
    public List<Visitante> getLista(String descricao) throws Exception{
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Visitante.class);
            
            if( descricao != null ){
                criteria.add( Restrictions.like("descricao", descricao, MatchMode.ANYWHERE ) );
            }
            
            return criteria.list();

        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Visitante> getLista(Date dataIni, Date dataFim) throws Exception {
        // conexao com o banco de dados;
        Session session = null;        
        String hql;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Visitante.class);
            
            if(dataIni == null && dataFim == null)
            {
                criteria.add(Restrictions.eq("dataVisita", new Date()));                
            }
            else if(dataIni != null && dataFim == null)
            {
                criteria.add(Restrictions.ge("dataVisita", dataIni)); 
            }
            else if(dataIni == null && dataFim != null)
            {
                criteria.add(Restrictions.le("dataVisita", dataFim)); 
            }            
            else
            {
                criteria.add(Restrictions.ge("dataVisita", dataIni));
                criteria.add(Restrictions.le("dataVisita", dataFim));
            }            
            
            criteria.addOrder(Order.desc("dataVisita"));
            criteria.addOrder(Order.asc("Nome"));
            
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
 
