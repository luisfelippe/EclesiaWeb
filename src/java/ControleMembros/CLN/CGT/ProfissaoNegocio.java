
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.ProfissaoDAO;
import java.util.List;
import ControleMembros.CLN.CDP.Profissao;
import exception.NegocioException;
import util.Entidade;
import util.Util;

/**
 *
 * @author Luis
 */
public class ProfissaoNegocio 
{    
    public List<Profissao> getLista(String descricao) throws NegocioException, Exception{
        List<Profissao> profissoes = null;
        
        if(Util.isPreenchidoPadrao(descricao)) {            
            profissoes = new ProfissaoDAO().getLista(descricao);
        }
        else
        {
            profissoes = new ProfissaoDAO().getLista(null);
        }
        
        if(profissoes == null || profissoes.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return profissoes;            
    }

    public Profissao getProfissao(long profissao) throws NegocioException, Exception {
        if(profissao < 0)
            throw new NegocioException("Favor informar uma profissao!");
        
        return new ProfissaoDAO().getProfissao(profissao);
    }
    
    public void salvar(Profissao p) throws NegocioException, Exception {
        if(p == null || p.getDescricao() == null || Util.isPreenchido(p.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para a profissão com pelo menos 3 caracteres!");
        
        new ProfissaoDAO().salvar(p);
    }
    
    public void excluir(Entidade e) throws NegocioException, Exception {
        if(e == null || e.getId() < 1 || (e instanceof Profissao) == false)
            throw new NegocioException("Favor informar uma profissao válida!");
        
        new ProfissaoDAO().Excluir(e);
    }
}
