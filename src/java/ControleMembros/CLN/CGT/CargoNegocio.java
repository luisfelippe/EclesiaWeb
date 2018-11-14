/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.CargoDAO;
import ControleMembros.CLN.CDP.Cargo;
import ControleMembros.CLN.CDP.Ministerio;
import exception.NegocioException;
import java.util.List;
import util.Entidade;
import util.Util;

/**
 *
 * @author luisfelippe
 */
public class CargoNegocio {
    public Cargo getNovoCargo(Ministerio m) {
        return new Cargo(m);
    }
    
    public List<Cargo> getLista() throws Exception {
        return new CargoDAO().getLista();
    }
    
    public List<Cargo> getLista(Ministerio m) throws Exception {
        if(m == null || m.getId() < 1)
            throw new NegocioException("Favor informar um ministério válido!");
        
        return new CargoDAO().getLista(m);
    }
    
    public void salvar(Cargo c) throws NegocioException, Exception {
        if(c == null || c.getDescricao() == null || !Util.isPreenchido(c.getDescricao(), 3))
            throw new NegocioException("Favor informar uma descrição para o cargo com pelo menos 3 caracteres!");
        
        if(c.getMinisterio() == null || c.getMinisterio().getId() < 1)
            throw new NegocioException("Favor informar um ministério válido para o cargo!");
        
        new CargoDAO().salvar(c);
    }
    
    public void excluir(Entidade e) throws NegocioException, Exception {
        if(e == null || e.getId() < 1 || (e instanceof Cargo) == false)
            throw new NegocioException("Favor informar um cargo válido!");
        
        new CargoDAO().Excluir(e);
    }
}
