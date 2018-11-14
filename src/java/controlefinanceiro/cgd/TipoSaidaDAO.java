/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cgd;

import ControleMembros.CGD.DAOBase;
import ControleMembros.CGD.HibernateUtil;
import controlefinanceiro.cln.cdp.TipoSaida;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LuísFelippe
 */
public class TipoSaidaDAO extends DAOBase {
    public List<TipoSaida> getLista(String descricao) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(TipoSaida.class);
            
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
}
