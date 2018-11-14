/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.EscolaridadeDAO;
import java.util.List;

/**
 *
 * @author Luis
 */
public class EscolaridadeNegocio {
    public List getLista() throws Exception {
        return new EscolaridadeDAO().getLista();
    }
}
