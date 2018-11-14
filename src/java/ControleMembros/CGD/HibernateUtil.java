/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Luis
 */
public class HibernateUtil {
        
    public static void main (String[] args)
    {
//        // conexao com o banco de dados;
//        Session session = null;
//        Usuario usr = new Usuario();
//        usr.setLogin("luis");
//        usr.setSenha("123");
//        try {
//
//            session = HibernateUtil.getSessionFactory().openSession();
//
//            Criteria criteria = session.createCriteria(Usuario.class);            
//            
//            criteria.add( Restrictions.eq("Login", usr.getLogin() ) );
//            criteria.add( Restrictions.eq("Senha", usr.getSenha() ) );           
//            
//            usr = (Usuario) criteria.list().get(0);
//            
//            System.out.println(usr.getNome());
//
//        } catch (Exception e) {
//             e.printStackTrace();
//        } finally {
//
//            if (session != null) {
//                session.close();
//            }
//
//        }
        try
        {
        System.out.println(new MembroDAO().getQtdMembrosOciosos());
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
