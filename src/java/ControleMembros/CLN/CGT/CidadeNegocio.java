/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.CidadeDAO;
import ControleMembros.CLN.CDP.Cidade;
import java.util.List;

/**
 *
 * @author LuísFelippe
 */
public class CidadeNegocio {
    public Cidade getNovaCidade() {
        return new Cidade();
    }
    
    public List<Cidade> getLista(long estado) throws Exception {
        return new CidadeDAO().getLista(estado);
    }
}
