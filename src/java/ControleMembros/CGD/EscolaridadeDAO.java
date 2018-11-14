package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Escolaridade;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class EscolaridadeDAO extends DAOBase {
    public List getLista() throws Exception {
        Session session = null;
        
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria q = session.createCriteria(Escolaridade.class);
            
            return q.list();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if(session != null && session.isOpen())
                session.close();
        }
    }
}
 
