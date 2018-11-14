package ControleMembros.CGD;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Usuario;
import ControleMembros.CLN.CGT.UsuarioNegocio;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO extends  DAOBase {
    
    public Membro efetuarLogin(Usuario usr) throws Exception
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Usuario.class);            
            
            criteria.add( Restrictions.eq("Login", usr.getLogin() ) );
            criteria.add( Restrictions.eq("Senha", usr.getSenha() ) );           
            
            if(criteria.list().isEmpty())
                throw new Exception("Login ou Senha inválidos ou Usuário não cadastrado!");
            
            usr = (Usuario) criteria.list().get(0);
            return usr.getMembro();

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public List<Membro> getLista(String nome) throws Exception{
        //@TODO acertar os critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Usuario.class);
            
            if( nome != null ){
                criteria.add( Restrictions.like("Nome", nome, MatchMode.ANYWHERE ) );                
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

    public Usuario getUsuario(Membro membro) throws Exception {
        // conexao com o banco de dados;
        Session session = null;
        Usuario usr;
        
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Usuario.class);            
            
            criteria.add( Restrictions.eq("membro", membro ) );          
            
            if(criteria.list().isEmpty())
                usr = new UsuarioNegocio().getNovoUsuario();
            else
                usr = (Usuario) criteria.list().get(0);
            
            return usr;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
}
 
