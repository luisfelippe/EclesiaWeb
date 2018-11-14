/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CLN.CDP.Telefone;
import exception.NegocioException;
import util.Entidade;
import util.Util;

/**
 *
 * @author Luis
 */
public class TelefoneNegocio {
    public Telefone getNovoTelefone() {
        return new Telefone();
    }
    
    public void validaTelefone(Telefone tel) throws NegocioException {
        if( !Util.isPreenchido(tel.getNumero(), 14) )
            throw new NegocioException("Favor informar um número válido");
        
        if(tel.getTipo()== null || tel.getTipo().getId() < 1)
            throw new NegocioException("Favor informar um tipo  de telefone válido!");
    }
    
//    public void excluir(Entidade item) throws NegocioException {
//        if(item == null || (item instanceof Telefone) == false || item.getId() < 1)
//            throw new NegocioException("Favor informar um telefone válido!");
//        
//        new TelefoneDAO().
//    }
}
