/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CLN.CDP.Endereco;
import exception.NegocioException;
import util.Util;

/**
 *
 * @author Luis
 */
public class EnderecoNegocio {
    public Endereco getNovoEndereco() {
        return new Endereco();
    }
    
    public void validaEndereco(Endereco end) throws NegocioException {
        if( !Util.isPreenchido(end.getLogradouro(), 3) )
            throw new NegocioException("Favor informar um logradouro válido");
        
        if(end.getBairro() == null || end.getBairro().getId() < 1)
            throw new NegocioException("Favor informar um bairro válido!");
    }
}
