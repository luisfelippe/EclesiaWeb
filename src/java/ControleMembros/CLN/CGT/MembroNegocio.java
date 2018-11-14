/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.MembroDAO;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.TipoAdmissao;
import exception.NegocioException;
import java.util.Date;
import java.util.List;
import util.Util;

/**
 *
 * @author Luis
 */
public class MembroNegocio {
    
    public Membro getNovoMembro() {
        return new Membro();
    }
    
    public void salvarMembro(Membro membro) throws NegocioException, Exception {
        if( ! Util.isPreenchidoPadrao( membro.getNome() ) )
            throw new NegocioException("O nome deve conter pelo menos 03 digitos!");
        
        if( ! Util.isCPFValido( membro.getCPF() ) )
            throw new NegocioException("Número de CPF inválido!");
        
        //validando a forma e data de admissaõ
        if(membro.getDataAdmissao() != null && membro.getTipoAdmissao() == null)
            throw new NegocioException("Favor informar o Tipo de Admissão!");
        else if(membro.getTipoAdmissao() != null && membro.getDataAdmissao() == null)
            throw new NegocioException("Favor informar a Data de Admissão!");
        
        //se o tipo de adimissao for batismo verifica o preenchimento
        if(membro.getTipoAdmissao() == TipoAdmissao.BATISMO && membro.getDataBatismo() == null)
            throw new NegocioException("Favor informar a Data de Batismo!");
        
        
        
        new MembroDAO().salvar(membro);
    }
    
    public List<Membro> getLista(String nome) throws NegocioException, Exception{
        List<Membro> membros = null;
        
        if(Util.isPreenchidoPadrao(nome)) {            
            membros = new MembroDAO().getLista(nome);
        }
        else
        {
            membros = new MembroDAO().getLista(null);
        }
        
        if(membros == null || membros.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return membros;            
    }
    
    public List<Membro> getLista(long cod, String nome) throws NegocioException, Exception{
        List<Membro> membros = null;
        
        if(cod > 0 && !Util.isPreenchidoPadrao(nome))
            membros = new MembroDAO().getLista(cod); //se for passado somente o código
        else if(cod == 0 && Util.isPreenchidoPadrao(nome))
            membros = new MembroDAO().getLista(nome); //se for passado somente o nome
        else if(cod == 0 && !Util.isPreenchidoPadrao(nome))
            membros = new MembroDAO().getLista(null); //se nada for passado
        else
            membros = new MembroDAO().getLista(cod, nome); //se ambos forem informados
        
        if(membros == null || membros.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return membros; 
    }    
    
    public List<Membro> getLista(Date dataIni, Date dataFim) throws NegocioException, Exception{
        List<Membro> membros = null;

        membros = new MembroDAO().getLista(dataIni, dataFim); //se for passado somente o código
        
        if(membros == null || membros.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return membros; 
    } 
    
    public List<Membro> getListaAniverMes(Date dataAtual) throws NegocioException, Exception{
        List<Membro> membros = null;

        membros = new MembroDAO().getListaAniverMes(dataAtual); //se for passado somente o código
            
        return membros; 
    }           
     
    public List<Membro> getLista(long cod, String nome, String cpf, String telefone, Date dataIni, Date dataFim) throws NegocioException, Exception {
        List<Membro> membros = null;

        membros = new MembroDAO().getLista(cod, nome, cpf, telefone, dataIni, dataFim); //se for passado somente o código
        
        if(membros == null || membros.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return membros; 
    }
    
    public Membro getMembro(long cod) throws Exception{
        
        if(cod < 1)
            throw new NegocioException("Informe um Código de Membro válido!");
        
        Membro memb = null;
        
        memb = new MembroDAO().getMembro(cod);
        
        if(memb == null)
            throw new NegocioException("Não foram encontrados registros");
            
        return memb; 
    }
    
    public void excluirMembro(Membro membro) throws NegocioException, Exception {
        if(membro == null || membro.getId() < 1)
            throw new NegocioException("É necessário informar um Membro a ser excluído!");
        
        new MembroDAO().Excluir(membro);
    }
    
    public long getQtdMembros() throws Exception {
        return new MembroDAO().getQtdMembros();
    }
    
    public long getQtdMembrosAtivos() throws Exception {
        return new MembroDAO().getQtdMembrosAtivos();
    }
    
    public long getQtdMembrosInativos() throws Exception {
        return new MembroDAO().getQtdMembrosInativos();
    }
    
    public long getQtdMembrosOciosos() throws Exception {
        return new MembroDAO().getQtdMembrosOciosos();
    }
    
    public long getQtdMembrosBatizados() throws Exception {
        return new MembroDAO().getQtdMembrosBatizados();
    }
}
