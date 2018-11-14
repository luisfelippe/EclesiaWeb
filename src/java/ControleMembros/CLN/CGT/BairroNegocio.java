/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.BairroDAO;
import ControleMembros.CLN.CDP.Bairro;
import java.util.List;

/**
 *
 * @author LuísFelippe
 */
public class BairroNegocio {
    public Bairro getNovoBairro(){
        return new Bairro();
    }
    
    public List<Bairro> getLista(long cid) throws Exception {
        return new BairroDAO().getLista(cid);
    }
}
