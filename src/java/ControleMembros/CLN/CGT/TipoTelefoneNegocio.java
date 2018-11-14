/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CLN.CDP.TipoTelefone;
import ControleMembros.CLN.CDP.TipoTelefone;
import ControleMembros.CGD.TipoTelefoneDAO;
import exception.NegocioException;
import java.util.List;
import util.Entidade;
import util.Util;

/**
 *
 * @author luisfelippe
 */
public class TipoTelefoneNegocio {
    public TipoTelefone getNovoTipoTelefone() {
        return new TipoTelefone();
    }
    
    public List<TipoTelefone> getLista() throws Exception {
        return new TipoTelefoneDAO().getLista();
    }
    
    public void salvar(TipoTelefone c) throws NegocioException, Exception {
        if(c == null || c.getDescricao() == null || !Util.isPreenchido(c.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para o tipo de telefone com pelo menos 3 caracteres!");                
        
        new TipoTelefoneDAO().salvar(c);
    }
    
    public void excluir(Entidade e) throws NegocioException, Exception {
        if(e == null || e.getId() < 1 || (e instanceof TipoTelefone) == false)
            throw new NegocioException("Favor informar um tipo de telefone válido!");
        
        new TipoTelefoneDAO().Excluir(e);
    }
}
