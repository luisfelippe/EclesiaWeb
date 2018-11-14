
package controlefinanceiro.cln.cgt;

import controlefinanceiro.cgd.CategoriaLancamentoDAO;
import controlefinanceiro.cln.cdp.CategoriaLancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import exception.NegocioException;
import java.util.List;
import util.Util;

/**
 *
 * @author luisfelippe
 */
public class CategoriaLancamentoNegocio {
    public List<CategoriaLancamento> getLista(String descricao) throws NegocioException, Exception{
        return new CategoriaLancamentoDAO().getLista(descricao);                   
    }
    
    public List<CategoriaLancamento> getLista(TipoLancamento l) throws NegocioException, Exception{
        return new CategoriaLancamentoDAO().getLista(l);                   
    }
    
    public void salvar(CategoriaLancamento t) throws NegocioException, Exception {
        if(t == null || t.getDescricao() == null || !Util.isPreenchido(t.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para o tipo de entrada com pelo menos 3 caracteres!");
        
        new CategoriaLancamentoDAO().salvar(t);
    }
}
