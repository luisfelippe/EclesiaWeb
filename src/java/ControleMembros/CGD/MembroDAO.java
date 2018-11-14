package ControleMembros.CGD;

import ControleMembros.CLN.CDP.DadosMinisteriais;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Usuario;
import java.text.SimpleDateFormat;
import util.Util;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class MembroDAO extends DAOBase {
    
    public List<Membro> getLista(String nome) throws Exception{
        //@TODO acertar os critérios de busca
        
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            
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
    
    public List<Membro> getLista(long cod) throws Exception {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            
            if( cod > 0) {                
                criteria.add( Restrictions.idEq(cod));
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
    
    public long getQtdMembros() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public long getQtdMembrosBatizados() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            criteria.add(Restrictions.isNotNull("dataBatismo"));
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public long getQtdMembrosAtivos() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            criteria.add(Restrictions.eq("inativo", false));
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public long getQtdMembrosInativos() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            criteria.add(Restrictions.eq("inativo", true));
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    /**
     * Método responsável por realizar a consulta e retornar a quantidade de membros que não estão alocados em ministério algum
     * @return
     * @throws Exception 
     */
    public long getQtdMembrosOciosos() throws Exception 
    {
        // conexao com o banco de dados;
        Session session = null;

        try 
        {

            session = HibernateUtil.getSessionFactory().openSession();
            
            DetachedCriteria dc = DetachedCriteria.forClass(DadosMinisteriais.class, "dm");
            dc.setProjection(Property.forName("membro"));

            Criteria criteria = session.createCriteria(Membro.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.add(Property.forName("id").notIn(dc));
            
            long qtd = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            
            return qtd;

        } catch (Exception e) {
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }
    }
    
    public List<Membro> getLista(long cod, String nome) throws Exception {        
            
        // conexao com o banco de dados;
        Session session = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Membro.class);
            
            if( nome != null && cod > 0){
                criteria.add( Restrictions.like("Nome", nome, MatchMode.ANYWHERE ) );
                criteria.add( Restrictions.idEq(cod));
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
    
    /**
     * Retorna uma lista contendo os aniversariantes do período
     * @param dataIni
     * @param dataFim
     * @return List<Membro>
     * @throws Exception 
     */
    public List<Membro> getLista(Date dataIni, Date dataFim) throws Exception {        
            
        // conexao com o banco de dados;
        Session session = null;
        Calendar cal = Calendar.getInstance();
        String hql;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query;
            
            if(dataIni == null && dataFim == null)
            {
                hql = "from Membro m "  
                        + "where month(m.DataNasc) = :mnasc " 
                        + "order by day(m.DataNasc)";  
                query = session.createQuery(hql);  
                query.setInteger("mnasc", (int) cal.get(Calendar.MONTH) + 1);  
            }
            else if(dataIni != null && dataFim == null)
            {
                hql = "from Membro m "  
                        + "where month(m.DataNasc) >= :mnasc " 
                        + "order by day(m.DataNasc)";  
                query = session.createQuery(hql);  
                query.setInteger("mnasc", dataIni.getMonth() + 1); 
            }
            else if(dataIni == null && dataFim != null)
            {
                hql = "from Membro m "  
                        + "where month(m.DataNasc) <= :mnasc " 
                        + "order by day(m.DataNasc)";  
                query = session.createQuery(hql);  
                query.setInteger("mnasc", dataFim.getMonth() + 1); 
            }            
            else
            {
                hql = "from Membro m "  
                        + "where month(m.DataNasc) between :mnascI and :mnascF " 
                        + "order by day(m.DataNasc)";  
                query = session.createQuery(hql);  
                query.setInteger("mnascI", dataIni.getMonth() + 1); 
                query.setInteger("mnascF", dataFim.getMonth() + 1); 
            }            
            
            return query.list();  

        } catch (Exception e) {            
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        }        
    }
    
    public List<Membro> getListaAniverMes(Date datAtual) throws Exception 
    {                    
        // conexao com o banco de dados;
        Session session = null;
        Calendar cal = Calendar.getInstance();
        String hql;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query;
            
            hql = "from Membro m "  
                    + "where month(m.DataNasc) = :mnasc " 
                    + "order by day(m.DataNasc)";  
            query = session.createQuery(hql);  
            query.setInteger("mnasc", (int) cal.get(Calendar.MONTH) + 1);  
            
            return query.list();  

        } 
        catch (Exception e) 
        {            
            throw e;
        } 
        finally 
        {
            if (session != null) {
                session.close();
            }

        }        
    }
    
    public List<Membro> getLista(long cod, String nome, String cpf, String telefone, Date dataIni, Date dataFim) throws Exception {
        
        // conexao com o banco de dados;
        Session session = null;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Membro.class);
            
            if( nome != null && Util.isPreenchidoPadrao(nome))
                criteria.add( Restrictions.like("Nome", nome, MatchMode.ANYWHERE ) );

            if( cod > 0)
                criteria.add( Restrictions.idEq(cod));
            
            if( cpf != null && Util.isPreenchidoExato(cpf, 14))
                criteria.add(Restrictions.eq("CPF", cpf));
            
            if( telefone != null && Util.isPreenchidoExato(telefone, 14))
                criteria.createCriteria("telefone").add(Restrictions.eq("Numero", telefone));            
            
            if(dataIni != null || dataFim != null)
            {
                SimpleDateFormat sdfm = new SimpleDateFormat("MM");
//                SimpleDateFormat sdfd = new SimpleDateFormat("dd");
                
                if(dataIni != null )
                    criteria.add(Restrictions.sqlRestriction("MONTH({alias}.DATANASC) >= '" + sdfm.format(dataIni) + "'"));

                if(dataFim != null)
                    criteria.add(Restrictions.sqlRestriction("MONTH({alias}.DATANASC) <= '" + sdfm.format(dataFim) + "'"));                       
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
    
    public Membro getMembro(long cod) throws Exception
    {
        // conexao com o banco de dados;
        Session session = null;        
        
        try 
        {
            session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Membro.class);
            
            System.out.println();
            System.out.println();
            System.out.println("cod--------="+cod);
            System.out.println();
            System.out.println();
            
            if( cod > 0)
                criteria.add( Restrictions.idEq(cod));
            
            return (Membro) criteria.uniqueResult();            

        } catch (Exception e) {            
            throw e;
        } finally {

            if (session != null) {
                session.close();
            }

        } 
    }
}
/* jbsld-abbaa-bjlop-zhecu-vbyes */