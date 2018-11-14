package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Profissao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class ProfissaoDAO extends DAOBase {
    public List<Profissao> getLista(String descricao) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Profissao.class);
            
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

    public Profissao getProfissao(long profissao) throws Exception {
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Profissao.class);
            
            criteria.add( Restrictions.eq("id", profissao ) );
            
            return (Profissao) criteria.uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
}
 
