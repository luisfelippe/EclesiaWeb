/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.EstadoDAO;
import ControleMembros.CLN.CDP.Estado;
import ControleMembros.CLN.CDP.Pais;
import java.util.List;

/**
 *
 * @author LuísFelippe
 */
public class EstadoNegocio {
    public Estado getNovoEstado() {
        return new Estado();
    }
    
    public List<Estado> getLista(Pais p) throws Exception {
        return new EstadoDAO().getLista(p);
    }
    
    public List<Estado> getLista() throws Exception {
        return this.getLista(null);
    }    
}
