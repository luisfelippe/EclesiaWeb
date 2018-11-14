/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cln.cgt;

import exception.NegocioException;
import controlefinanceiro.cgd.TipoSaidaDAO;
import controlefinanceiro.cln.cdp.TipoSaida;
import java.util.List;
import util.Util;

/**
 *
 * @author LuísFelippe
 */
public class TipoSaidaNegocio {
    public List<TipoSaida> getLista(String descricao) throws NegocioException, Exception{
        return new TipoSaidaDAO().getLista(null);                    
    }
    
    public void salvar(TipoSaida t) throws NegocioException, Exception {
        if(t == null || t.getDescricao() == null || !Util.isPreenchido(t.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para o tipo de entrada com pelo menos 3 caracteres!");
        
        new TipoSaidaDAO().salvar(t);
    }
}
