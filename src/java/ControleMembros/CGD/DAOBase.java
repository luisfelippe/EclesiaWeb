package ControleMembros.CGD;

import util.Entidade;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DAOBase {
 
	public void salvar(Entidade entidade) throws Exception{
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            tx = session.beginTransaction();
            
            session.saveOrUpdate(entidade);
            
            tx.commit();
        } 
        catch(Exception e){
            if(tx != null)
                tx.rollback();
            
            throw e;
        } 
        finally {
            if(session != null)
                session.close();
        }
    }
    
    public void Excluir(Entidade entidade) throws Exception{
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            
            tx = session.beginTransaction();
            
            session.delete(entidade);
            
            tx.commit();
        } 
        catch(Exception e){
            if(tx != null)
                tx.rollback();
            
            throw e;
        } 
        finally {
            if(session != null)
                session.close();
        }
    }   
}
 
