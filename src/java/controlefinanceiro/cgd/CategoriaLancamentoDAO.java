/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cgd;

import ControleMembros.CGD.DAOBase;
import ControleMembros.CGD.HibernateUtil;
import controlefinanceiro.cln.cdp.CategoriaLancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luisfelippe
 */
public class CategoriaLancamentoDAO extends DAOBase {
    public List<CategoriaLancamento> getLista(String descricao) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(CategoriaLancamento.class);
            
            if( descricao != null && !descricao.trim().isEmpty()){
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
    
    public List<CategoriaLancamento> getLista(TipoLancamento t) throws Exception{
        
        //@TODO acertar critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(CategoriaLancamento.class);
            
            criteria.add(Restrictions.eq("tipo", t));            
            
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
