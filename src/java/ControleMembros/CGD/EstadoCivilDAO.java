package ControleMembros.CGD;

import ControleMembros.CLN.CDP.EstadoCivil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class EstadoCivilDAO extends DAOBase {
    public List<EstadoCivil> getLista(String descricao) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(EstadoCivil.class);
            
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

    public EstadoCivil getEstadoCivil(long estadoCivil) throws Exception {
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(EstadoCivil.class);
            
            criteria.add( Restrictions.eq("id", estadoCivil ) );
            
            return (EstadoCivil) criteria.uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
}
 
