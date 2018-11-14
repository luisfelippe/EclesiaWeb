/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.MinisterioDAO;
import ControleMembros.CLN.CDP.Ministerio;
import exception.NegocioException;
import java.util.List;
import util.Entidade;
import util.Util;

/**
 *
 * @author luisfelippe
 */
public class MinisterioNegocio {
    public Ministerio getNovoMinisterio() {
        return new Ministerio();
    }
    
    public List<Ministerio> getLista() throws Exception {
        return new MinisterioDAO().getLista();
    }
    
    public void salvar(Ministerio m) throws NegocioException, Exception {
        if(m == null || m.getDescricao() == null || !Util.isPreenchido(m.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para o ministério com pelo menos 3 caracteres!");
        
        new MinisterioDAO().salvar(m);
    }
    
    public long getQtdMinisterios() throws Exception {
        return new MinisterioDAO().getQtdMinisterios();
    }
    
    public void excluir(Entidade e) throws NegocioException, Exception {
        if(e == null || e.getId() < 1 || (e instanceof Ministerio) == false)
            throw new NegocioException("Favor informar um ministério válido!");
        
        new MinisterioDAO().Excluir(e);
    }
}
