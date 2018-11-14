/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.DadosMinisteriaisDAO;
import ControleMembros.CLN.CDP.DadosMinisteriais;
import exception.NegocioException;

/**
 *
 * @author luisfelippe
 */
public class DadosMinisteriaisNegocio {
    public DadosMinisteriais getNovoDadosMinisteriais() {
        return new DadosMinisteriais();
    }
    
    public void salvar(DadosMinisteriais d) throws NegocioException, Exception {
        this.validaDadosMinisteriais(d);
        
        new DadosMinisteriaisDAO().salvar(d);
    }
    
    public void validaDadosMinisteriais(DadosMinisteriais dados) throws NegocioException {
        if(dados == null)
            throw new NegocioException("Favor informar um conjunto de dados ministeriais válido");
        
        if(dados.getMembro() == null || dados.getMembro().getId() < 1)
            throw new NegocioException("Favor informar um membro válido!");
        
        if( dados.getDataInicial() == null )
            throw new NegocioException("Favor informar uma data inicial!");
        
        if( dados.getCargo() == null || dados.getCargo().getId() < 1)
            throw new NegocioException("Favor informar um cargo válido!");
    }
}
