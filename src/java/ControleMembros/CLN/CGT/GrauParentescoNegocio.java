/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.GrauParentescoDAO;
import ControleMembros.CLN.CDP.GrauParentesco;
import java.util.List;

/**
 * Implementa a camada de negócio de um GrauParentesco
 * @author LuísFelippe
 */
public class GrauParentescoNegocio {
    public GrauParentesco getNovoGrauParentesco() {
        return new GrauParentesco();
    }
    
    public List<GrauParentesco> getLista() throws Exception {
        return new GrauParentescoDAO().getLista();
    }
}
